package com.whh.cctalk.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.whh.cctalk.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * The login page
 */
class LoginActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        TAG = LoginActivity::class.java.simpleName
    }

    override fun initView() {
        btn_login.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
    }

}
