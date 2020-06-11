package com.whh.cctalk.activity

import com.whh.cctalk.R

/**
 * The Template page
 */
class TemplateActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_template
    }

    override fun initData() {
        TAG = TemplateActivity::class.java.simpleName
    }

    override fun initView() {
        //
    }

}
