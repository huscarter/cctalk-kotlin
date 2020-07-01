package com.whh.cctalk.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.whh.cctalk.R
import com.whh.cctalk.adapter.ContractListAdapter
import com.whh.cctalk.business.ContractBusiness
import com.whh.cctalk.mode.bean.UserBean
import com.whh.cctalk.view.CommonDivide
import kotlinx.android.synthetic.main.fragment_contract_list.refresh_layout
import kotlinx.android.synthetic.main.fragment_contract_list.rv

/**
 * The contract list page
 */
class ContractListFragment : BaseFragment() {
    private var adapter: ContractListAdapter? = null
    private val userList = ArrayList<UserBean>()
    private val contractBusiness = ContractBusiness()

    override fun getLayout(): Int {
        return R.layout.fragment_contract_list
    }

    override fun initData() {
        TAG = ContractListFragment::class.java.simpleName

        if (adapter == null) {
            adapter = ContractListAdapter(context, userList)
            contractBusiness.getContractList(::afterGetList)
        }
    }

    override fun initView() {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        rv.addItemDecoration(CommonDivide(context, LinearLayoutManager.VERTICAL))
    }

    private fun afterGetList(list: List<UserBean>?) {
        if (list != null) {
            userList.addAll(list)
        }
        adapter?.notifyDataSetChanged()

        if (refresh_layout?.isRefreshing == true) {
            refresh_layout?.finishRefresh()
        }
    }

}
