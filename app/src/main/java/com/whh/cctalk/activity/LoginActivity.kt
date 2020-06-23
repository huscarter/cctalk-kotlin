package com.whh.cctalk.activity

import android.content.Intent
import com.whh.cctalk.Global
import com.whh.cctalk.NetConfig
import com.whh.cctalk.R
import com.whh.cctalk.bean.UserBean
import com.whh.cctalk.business.UserBusiness
import com.whh.cctalk.util.CommonUtil
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.util.ShareUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * The login page
 */
class LoginActivity : BaseActivity() {
    private  val userBusiness = UserBusiness()
    
    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        TAG = LoginActivity::class.java.simpleName
    }

    override fun initView() {
        val mobile = ShareUtil.getString(Global.MOBILE,null)
        LogUtil.i(TAG,"mobile:$mobile")
        if(!CommonUtil.isEmpty(mobile)){
            et_mobile.setText(mobile)
            et_mobile.setSelection(mobile!!.length)
        }
        //
        btn_login.setOnClickListener {
            getImToken()
        }
    }

    private fun callback(userBean:UserBean) {
        LogUtil.i(TAG,"user:${userBean.code}")
        if(userBean.code == NetConfig.SUCCESS){
            ShareUtil.setString(Global.TOKEN,userBean.token)
            context?.startActivity(Intent(context,MainActivity::class.java))
            finish()
        }
    }

    private fun getImToken(){
        val mobile = et_mobile.text.toString()
        if(CommonUtil.isEmpty(mobile)){
            CommonUtil.showToast(context!!,"Please input mobile")
            return
        }
        ShareUtil.setString(Global.MOBILE,mobile)
        userBusiness.getImToken(context,mobile, ::callback)
    }

}
