package com.whh.cctalk.net.crt

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by huscarter@.163.com on 2020/6/13
 *
 * 提供证书获取认证类, 此类适格式为crt和cer的证书
 */
class SSlContextProvider private constructor() {
    private var sslContext: SSLContext? = null
    private var tmf: TrustManagerFactory? = null

    companion object {
        private val TAG = SSlContextProvider::class.java.simpleName

        val instance: SSlContextProvider by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SSlContextProvider()
        }
    }

    /**
     * 此方法需要Application中做初始化，以后的网络请求中都不需要再次调用，直接使用已创建的实例
     *
     * @param context
     */
    fun initialize(context: Context) {
        try {
            val ins = arrayOf(
                context.assets.open("ssl/server_2017.cer"),
                context.assets.open("ssl/server_2018.cer")
            )
            holderSSL(ins)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 证书加载
     */
    private fun holderSSL(ins: Array<InputStream?>) {
        try { // 信任指定证书
            val cf = CertificateFactory.getInstance("X.509")
            val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
            keystore.load(null)
            for (i in ins.indices) {
                val certificate = cf.generateCertificate(ins[i])
                keystore.setCertificateEntry(i.toString(), certificate)
                try {
                    if (ins[i] != null) {
                        ins[i]!!.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf?.init(keystore)
            // Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS")
            sslContext?.init(null, tmf?.trustManagers, SecureRandom())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val socketFactory: SSLSocketFactory
        get() {
            if (sslContext == null) {
                throw NullPointerException("SSLContext null point exception! Please initialize first.")
            }
            return sslContext!!.socketFactory
        }

    val trustManager: X509TrustManager
        get() {
            if (tmf == null) {
                throw NullPointerException("TrustManagerFactory null point exception! Please initialize first.")
            }
            val managers = tmf!!.trustManagers
            check(!(managers.size != 1 || managers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + Arrays.toString(managers)
            }
            return managers[0] as X509TrustManager
        }
}