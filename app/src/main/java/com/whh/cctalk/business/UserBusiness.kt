package com.whh.cctalk.business

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.whh.cctalk.activity.LoginActivity
import com.whh.cctalk.mode.bean.UserBean
import com.whh.cctalk.net.IUser
import com.whh.cctalk.net.retrofit.CommonObserver
import com.whh.cctalk.net.retrofit.RxRequest
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.util.ShareUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.rong.imlib.RongIMClient

/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明: The business of user
 * <p>
 */
class UserBusiness : BaseBusiness() {
    companion object{
        val TAG: String = UserBusiness::class.java.simpleName
    }

    /**
     * To get the im token
     */
    fun getImToken(context: Context?, userId:String, callback: (userBean: UserBean)->Unit) {
        val param = HashMap<String, String>()
        param["userId"] = userId

        LogUtil.i(TAG,"getImToken param:$param")

        RxRequest.create(IUser::class.java, null).getImToken(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CommonObserver<UserBean>(context) {
                override fun onNext(userBean: UserBean) {
                    super.onNext(userBean)
                    callback(userBean)
                }
            })
    }

    /**
     *
     */
    fun loginOut(activity:Activity?,needPush:Boolean){
        RongIMClient.getInstance().disconnect(needPush)
        ShareUtil.clearData()
        activity?.startActivity(Intent(activity,LoginActivity::class.java))
        activity?.finish()
    }
}