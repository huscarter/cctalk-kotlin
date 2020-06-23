package com.whh.cctalk.net.retrofit

import com.google.gson.Gson
import com.whh.cctalk.NetConfig
import com.whh.cctalk.net.http.OkHttpClientProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by huscarter@163.com on 2020/6/13
 * <p>
 * 网络请求封装接口类
 */
object RxRequest {

    internal val TAG = RxRequest::class.java.simpleName

    /**
     * 请求接口的版本号
     */
    const val DEFAULT_VERSION = 1

    /**
     * 默认请求是比不重新请求
     */
    const val RETRY_REQUEST = false

    fun getRetrofitBuilder(token: String?): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(NetConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(OkHttpClientProvider.instance.getClient(DEFAULT_VERSION, token))
    }

    fun <T> create(clazz:Class<T>,token: String?):T{
        return getRetrofitBuilder(token).build().create(clazz)
    }
}