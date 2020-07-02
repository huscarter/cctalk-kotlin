package com.whh.cctalk.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.activity.ChatActivity
import com.whh.cctalk.util.CommonUtil
import com.whh.cctalk.util.DateUtil
import io.rong.imlib.model.Conversation
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.item_chat_list.view.*
import java.util.Date

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明:The adapter of chat list
 * <p>
 */
class ChatListAdapter(context: Context?,list: List<Conversation>) : BaseListAdapter<Conversation>(context,list) {

    /**
     *
     */
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val conversation = list[position]
        if(CommonUtil.isEmpty(conversation.senderUserName)){
            holder.itemView.tv_name.text = CommonUtil.secretPhone(conversation.targetId)
        }else{
            holder.itemView.tv_name.text = conversation.senderUserName
        }
        if(conversation.latestMessage is TextMessage){
            holder.itemView.tv_content.text = (conversation.latestMessage as TextMessage).content
        }else{

        }
        holder.itemView.tv_time.text = DateUtil.formatChatTime(conversation.sentTime)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Global.TARGET_ID,conversation.targetId)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_chat_list
    }
}
