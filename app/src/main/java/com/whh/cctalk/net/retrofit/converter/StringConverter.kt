package com.whh.cctalk.net.retrofit.converter

import retrofit2.Converter
import java.io.IOException

/**
 * Created by huscarter@163.com on 2020/6/13
 * <p>
 * 服务于RxRequest，为RestAdapter返回String提供Converter
 */
class StringConverter : Converter<Any?, Any?> {

    @Throws(IOException::class)
    override fun convert(value: Any?): Any? {
        return null
    }
}