package com.whh.cctalk.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.activity.ChatActivity
import com.whh.cctalk.mode.bean.UserBean
import com.whh.cctalk.util.DateUtil
import kotlinx.android.synthetic.main.item_chat_list.view.*
import java.util.*

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明: The adapter of contract
 * <p>
 */
class ContractListAdapter(context: Context?, list: List<UserBean>) : BaseListAdapter<UserBean>(context,list) {

    /**
     *
     */
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val user = list[position]

        holder.itemView.tv_name.text = user.userName?:user.userId
        holder.itemView.tv_time.text = DateUtil.formatChatTime(user.sentTime)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Global.TARGET_ID,user.userId)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_contract_list
    }
}
