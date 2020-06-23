package com.whh.cctalk.adapter

import com.whh.cctalk.R
import io.rong.imlib.model.Conversation

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明:
 * <p>
 */
class ChatListAdapter(list: List<Conversation>) : BaseListAdapter<Conversation>(list) {

    /**
     *
     */
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.item_chat_list
    }
}
