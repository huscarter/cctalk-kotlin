package com.whh.cctalk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.whh.cctalk.util.LogUtil

/**
 * Create by huscarter@163.com on 2020/6/11
 * <p>
 * 类说明:<BR/>
 * 接受传值:<BR/>
 * 对外传值:<BR/>
 */
abstract class BaseFragment:Fragment(){
    companion object{
        var TAG = BaseFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        initData()
        LogUtil.i(TAG,"onViewCreated")
        //
        initView()
    }

    /**
     *
     */
    abstract fun getLayout():Int

    /**
     *
     */
    abstract fun initData()

    /**
     *
     */
    abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.i(TAG,"onDestroyView")
    }
}