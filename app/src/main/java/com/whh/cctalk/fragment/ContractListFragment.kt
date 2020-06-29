package com.whh.cctalk.fragment

import com.whh.cctalk.R

/**
 * The contract list page
 */
class ContractListFragment :  BaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_contract_list
    }

    override fun initData() {
        TAG = ContractListFragment::class.java.simpleName
    }

    override fun initView() {
        //
    }

}
