package com.whh.cctalk.fragment

import com.whh.cctalk.R
import com.whh.cctalk.business.UserBusiness
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * The personal page
 */
class PersonalFragment : BaseFragment() {
    private val userBusiness = UserBusiness()

    override fun getLayout(): Int {
        return R.layout.fragment_personal
    }

    override fun initData() {
        //
    }

    override fun initView() {
        btn_login_out.setOnClickListener {
            userBusiness.loginOut(activity,false)
        }
    }
}
