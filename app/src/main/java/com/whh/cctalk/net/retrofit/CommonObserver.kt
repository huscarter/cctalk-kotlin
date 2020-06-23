package com.whh.cctalk.net.retrofit

import android.app.Activity
import android.content.Context
import androidx.annotation.CallSuper
import com.whh.cctalk.util.CommonUtil
import com.whh.cctalk.view.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by huscarter@163.com on 2020/6/13
 * <p>
 * 1. 建议onError给出提示信息，即需要在重写onError的时候传入nulll值。
 * <p>
 * 2. 如果不想提示任何信息，有两种方法：1)实例化时Context/Activity传入null；2)请重写onError传入null。
 *
 */
abstract class CommonObserver<T> : Observer<T> {
    /**
     *
     */
    private var context: Context? = null
    /**
     *
     */
    private var showDialog = true

    /**
     *
     */
    private var dialog:LoadingDialog? = null

    companion object {
        private val TAG = CommonObserver::class.java.simpleName
        private const val DELAY_TIME = 200
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val REQUEST_TIMEOUT = 408
        private const val INTERNAL_SERVER_ERROR = 500
        private const val NOT_IMPLEMENTED = 501
        private const val BAD_GATEWAY = 502
        private const val SERVICE_UNAVAILABLE = 503
        private const val GATEWAY_TIMEOUT = 504
    }

    /**
     * @param context 用于提示信息
     */
    constructor(context: Context?,showDialog:Boolean) {
        this.context = context
        this.showDialog = showDialog
    }

    /**
     * @param context 用于提示信息
     */
    constructor(context: Context?) {
        this.context = context
    }

    @CallSuper
    override fun onNext(t: T) {
        closeDialog()
    }

    override fun onComplete() {
        closeDialog()
    }

    override fun onSubscribe(d: Disposable) {
        showDialog()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        closeDialog()
    }

    private fun showDialog(){
        if(showDialog&&context!=null){
            dialog = LoadingDialog(context!!)
            dialog?.show()
        }
    }

    private fun closeDialog(){
        if(showDialog&&dialog!=null){
            dialog?.dismiss()
        }
    }
}