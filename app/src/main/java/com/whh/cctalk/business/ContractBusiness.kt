package com.whh.cctalk.business

import com.whh.cctalk.mode.bean.UserBean
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明: The business of contract list
 * <p>
 */
class ContractBusiness : BaseBusiness() {
    //
    companion object {
        val TAG: String = ContractBusiness::class.java.simpleName
        private const val MESSAGE_PAGE_SIZE = 50
    }

    /**
     * To get the contract
     */
    fun getContractList(callback:(List<UserBean>)->Unit){

        Observable.timer(1,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Long>{
            override fun onComplete() {
                //
            }

            override fun onSubscribe(d: Disposable) {
                //
            }

            override fun onNext(t: Long) {
                var list = ArrayList<UserBean>()
                list.add(UserBean("17764503986"))
                list.add(UserBean("18864503986"))

                callback(list)
            }

            override fun onError(e: Throwable) {
                //
            }
        })

    }
}