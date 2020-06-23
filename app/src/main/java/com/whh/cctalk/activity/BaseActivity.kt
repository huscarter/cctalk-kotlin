package com.whh.cctalk.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whh.cctalk.util.LogUtil

/**
 * Create by huscarter@163.com on 2020/6/11
 * <p>
 * 类说明:<BR/>
 * 接受传值:<BR/>
 * 对外传值:<BR/>
 */
abstract class BaseActivity : AppCompatActivity() {
    var context: Context? = null
    var activity: Activity? = null

    companion object {
        var TAG = BaseActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayout())

        context = this

        activity = this

        //
        initData()

        LogUtil.i(TAG, "onCreate")

        //
        initView()
    }

    /**
     *
     */
    abstract fun getLayout(): Int

    /**
     *
     */
    abstract fun initData()

    /**
     *
     */
    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }

}