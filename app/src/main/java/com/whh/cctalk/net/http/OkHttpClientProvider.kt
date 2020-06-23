package com.whh.cctalk.net.http

import com.google.gson.Gson
import com.whh.cctalk.BuildConfig
import com.whh.cctalk.Global
import com.whh.cctalk.net.crt.SSlContextProvider
import com.whh.cctalk.util.EncodeUtil
import com.whh.cctalk.util.LogUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Created by huscarter@163.com on 2020/6/13
 *
 * 配置ok http 初始化信息, 请求分3步,第一步连接,第二部发送请求,第三步获取数据
 */

class OkHttpClientProvider private constructor() {

    companion object {
        private val TAG = OkHttpClientProvider::class.java.simpleName
        /**
         * 请求头中的User-Agent定义
         */
        var UA = "platform:Android,version:" + BuildConfig.VERSION_NAME
        /**
         * 设置连接超时时间
         */
        const val HTTP_CLIENT_CONNECT_TIMEOUT = 30
        /**
         * 设置写入超时时间
         */
        const val HTTP_CLIENT_WRITE_TIMEOUT = 30
        /**
         * 设置读取时间
         */
        const val HTTP_CLIENT_READ_TIMEOUT = 30

        val instance: OkHttpClientProvider by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpClientProvider()
        }
    }

    /**
     * okhttp client 池,根据version和token有无创建不同的client
     */
    private val clients: MutableMap<String, OkHttpClient> = Hashtable()

    /**
     * 通过version和token获取okhttp client
     * 有token的key为token[version],无token的key为[version]
     *
     * @param version
     * @param token
     * @return
     */
    fun getClient(version: Int, token: String?): OkHttpClient? {
        val target = getUniqueKey(version, token)
        val client: OkHttpClient?
        client = if (clients[target] != null) {
            clients[target]
        } else {
            createClient(version, token)
        }
        return client
    }

    /**
     * 创建client 并添加到client池。
     * 这里为了减少client的创建，只判别version和token两个条件，retry被忽略
     *
     * @param version
     * @param token
     * @return
     */
    private fun createClient(version: Int, token: String?): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        config(builder, version, token)
        val client = builder.build()
        clients[getUniqueKey(version, token)] = client
        LogUtil.i(TAG, "clients size : " + clients.size)
        return client
    }

    /**
     * 通过version和token获取okhttp client
     * 有token的key为token[version],无token的key为[version]
     *
     * @param version
     * @param token
     * @return
     */
    fun getClientSSL(version: Int, token: String?): OkHttpClient? {
        val target = getUniqueKey(version, token)
        val client: OkHttpClient?
        client = if (clients[target] != null) {
            clients[target]
        } else {
            createClientSSL(version, token)
        }
        return client
    }

    /**
     * Close and remove all idle connections in the pool,if the pool is too big.
     *
     * @param client
     */
    private fun evictClient(client: OkHttpClient) {
        if (client.connectionPool().connectionCount() > HTTP_CLIENT_CONNECT_TIMEOUT) {
            client.connectionPool().evictAll()
        }
    }

    /**
     * 一般退出app调用，清空okhttp client缓存，因为期header保有上个帐号的token
     */
    fun clear() {
        for (key in clients.keys) {
            if (clients[key] != null) {
                clients[key]!!.connectionPool().evictAll()
            }
        }
        clients.clear()
        LogUtil.i(TAG, "clients size:" + clients.size)
    }

    /**
     * 创建client 并添加到client池。
     * 这里为了减少client的创建，只判别version和token两个条件，retry被忽略
     *
     * @param version
     * @param token
     * @return
     */
    private fun createClientSSL(version: Int, token: String?): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        config(builder, version, token)
        builder.sslSocketFactory(
            SSlContextProvider.instance.socketFactory,
            SSlContextProvider.instance.trustManager
        )
        val client = builder.build()
        clients[getUniqueKey(version, token)] = client
        LogUtil.i(TAG, "clients size : " + clients.size)
        return client
    }

    /**
     * 生成client对应的key
     *
     * @return
     */
    private fun getUniqueKey(version: Int, token: String?): String {
        return (if (token == null || token.isEmpty()) "" else token) + version
    }

    /**
     * 配置网络延迟，header和logger等
     *
     * @param builder
     * @param version
     * @param token
     */
    private fun config(
        builder: OkHttpClient.Builder,
        version: Int,
        token: String?
    ) {
        builder.connectTimeout(HTTP_CLIENT_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(HTTP_CLIENT_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(HTTP_CLIENT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        builder.retryOnConnectionFailure(retry);
        // to add header
        builder.addNetworkInterceptor(HeaderInterceptor(version, token))
        // to add log
        if (BuildConfig.DEBUG) { // 自定义的log日志
            builder.addNetworkInterceptor(LoggingInterceptor())
        }
    }

    /**
     * Header拦截器
     */
    private inner class HeaderInterceptor(private val version: Int, private val token: String?) :
        Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            // to add agent
//            builder.addHeader("User-Agent", UA)
            //为解决 http://blog.csdn.net/zhangteng22/article/details/52233126 问题
            builder.addHeader("Connection", "close")
            builder.addHeader("Accept", "application/vnd.trading.v$version+json")
            // to add Im header
            builder.addHeader("App-Key", Global.IM_APP_KEY)
            val nonce = Random(19970101).nextInt().toString()
            // 随机数，不超过 18 个字符
            builder.addHeader("Nonce", nonce)
            // 从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的毫秒数
            val timestamp = Date().time.toString()
            builder.addHeader("Timestamp", timestamp)
            // 将系统分配的 App Secret、Nonce (随机数)、Timestamp (时间戳)三个字符串按先后顺序拼接成一个字符串并进行 SHA1 哈希计算
//            LogUtil.i(TAG,"key:${Global.IM_APP_KEY},nonce:$nonce,timestamp:$timestamp")
            builder.addHeader("Signature", EncodeUtil.sha1(Global.IM_APP_SECRET+nonce+timestamp))

            if (null == token || "" == token || "null" == token) { // no token
            } else {
                builder.addHeader("Authorization", "Bearer {$token}")
            }
            return chain.proceed(builder.build())
        }

    }

    /**
     * log拦截器
     */
    private inner class LoggingInterceptor : Interceptor {
        private val TAG = "OkHttp"

        private val UTF8 = Charset.forName("UTF-8")

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val tStart = System.nanoTime()
            LogUtil.v(
                TAG,
                String.format(
                    "request %s on %s %n %s body:%s",
                    request.url(),
                    chain.connection(),
                    request.headers(),
                    Gson().toJson(request.body())
                )
            )
            val response = chain.proceed(request)
            val tEnd = System.nanoTime()
            // 打印请求耗时
//            Logger.v(TAG, String.format("response for %s in %.1fms%n%s", response.request().url(), (t_end - t_start) / 1e6d, response.headers()));
            val responseBody = response.body()
            val contentLength = responseBody!!.contentLength()
            val mediaType = response.body()!!.contentType()
            /**
             * 解决使用body.string()只能使用一次的问题
             */
//            if(MediaType.parse("application/json").equals(media_type)){ // 因为打印json，具有可读性
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            var charset = UTF8
            if (mediaType != null && charset != null) {
                charset = try {
                    mediaType.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    e.printStackTrace()
                    return response
                }
            }
            // content type 已经做了可读性分析，此处判断可省略
            if (!isPlaintext(buffer)) { //                Logger.v(TAG, "response body: Body omitted.(Maybe is a file)");
                return response
            }
            if (contentLength != 0L) {
                try { // normal json
                    LogUtil.v(
                        TAG,
                        String.format(
                            "response for %s in %.1fms%n%s",
                            response.request().url(),
                            (tEnd - tStart) / 1e6,
                            response.headers()
                        ) + "response body: " + buffer.clone().readString(charset)
                    );
//                     format json
//                    Logger.v(TAG, String.format("response for %s in %.1fms%n%s",response.request().url(), (t_end - t_start) / 1e6, response.headers()) + "response body: " + new JSONObject(buffer.clone().readString(charset)).toString(1));
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return response
        }

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        private fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false
            }
        }

    }
}