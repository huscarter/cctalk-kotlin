package com.whh.cctalk.net

import com.whh.cctalk.mode.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Create by huscarter@163.com on 2020/6/13
 * <p>
 * 类说明: Im网络请求接口
 */
interface IUser {

    @FormUrlEncoded
    @POST("user/getToken.json")
    fun getImToken(@FieldMap param: Map<String, String>): @JvmSuppressWildcards Observable<UserBean>

}