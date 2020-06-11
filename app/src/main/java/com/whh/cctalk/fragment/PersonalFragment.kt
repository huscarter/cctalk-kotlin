package com.whh.cctalk.fragment

import android.content.Intent
import com.whh.cctalk.R
import com.whh.cctalk.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * The personal page
 */
class PersonalFragment : BaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_personal
    }

    override fun initData() {
        //
    }

    override fun initView() {
        btn_login_out.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}
