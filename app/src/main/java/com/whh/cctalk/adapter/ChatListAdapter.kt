package com.whh.cctalk.adapter

import com.whh.cctalk.R
import com.whh.cctalk.util.CommonUtil
import com.whh.cctalk.util.DateUtil
import io.rong.imlib.model.Conversation
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.item_chat_list.view.*
import java.util.Date

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

        val conversation = list[position]
        if(CommonUtil.isEmpty(conversation.senderUserName)){
            holder.itemView.tv_name.text = conversation.targetId
        }else{
            holder.itemView.tv_name.text = conversation.senderUserName
        }
        if(conversation.latestMessage is TextMessage){
            holder.itemView.tv_content.text = (conversation.latestMessage as TextMessage).content
        }else{

        }
        holder.itemView.tv_time.text = DateUtil.format(Date(conversation.sentTime),"YY/MM/dd HH:MM")
    }

    override fun getLayout(): Int {
        return R.layout.item_chat_list
    }
}
