package com.whh.cctalk.activity

import android.content.Intent
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.util.ShareUtil

/**
 * Create by huscarter@163.com on 2020/6/18
 * <p>
 * 类说明:
 */
class LaunchActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_launch
    }

    /**
     *
     */
    override fun initData() {
        if (ShareUtil.getString(Global.TOKEN, null) == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }

    /**
     *
     */
    override fun initView() {
        //
    }
}