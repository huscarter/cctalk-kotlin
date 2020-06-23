package com.whh.cctalk

import android.app.Application
import com.whh.cctalk.util.ShareUtil
import io.rong.imlib.RongIMClient

/**
 * Create by huscarter@163.com on 2020/6/18
 * <p>
 * 类说明:
 * <p>
 */
class App :Application(){

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()

        init()
    }

    /**
     *
     */
    private fun init(){
        ShareUtil.init(baseContext)

        RongIMClient.init(baseContext,Global.IM_APP_KEY,true)
    }
}
