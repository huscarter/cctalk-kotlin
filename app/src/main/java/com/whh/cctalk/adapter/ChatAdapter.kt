package com.whh.cctalk.adapter

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.util.DateUtil
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.util.ShareUtil
import io.rong.imlib.model.Message
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.android.synthetic.main.item_chat_list.view.tv_time
import java.util.*

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明: The adapter of contract
 * <p>
 */
class ChatAdapter(context: Context?, list: List<Message>) :
    BaseListAdapter<Message>(context, list) {

    companion object{
        private val TAG = ChatAdapter::class.java.simpleName
    }

    private val minDis = 300000

    /**
     *
     */
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val msg = list[position]
        val msgContent = msg.content

        if(position == list.size - 1){
            holder.itemView.tv_time.visibility = View.VISIBLE
            holder.itemView.tv_time.text = DateUtil.formatChatTime(msg.sentTime)
        }else{
//            LogUtil.i(TAG,"${DateUtil.format(Date(msg.sentTime))},${DateUtil.format(Date(list[position+1].sentTime))}}")
            if (msg.sentTime-list[position+1].sentTime > minDis) {
                holder.itemView.tv_time.visibility = View.VISIBLE
                holder.itemView.tv_time.text = DateUtil.formatChatTime(msg.sentTime)
            } else {
                holder.itemView.tv_time.visibility = View.GONE
            }
        }
        //
        val ivPortrait = ImageView(context)
        val portraitSize = context!!.resources.getDimension(R.dimen.portrait_size_small).toInt()
        ivPortrait.layoutParams = LinearLayout.LayoutParams(portraitSize,portraitSize)
        ivPortrait.setImageDrawable(context!!.resources.getDrawable(R.drawable.circle_portrait))
        //
        val tvContent = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = context!!.resources.getDimension(R.dimen.margin).toInt()
        layoutParams.rightMargin = context!!.resources.getDimension(R.dimen.margin).toInt()
        tvContent.layoutParams = layoutParams
        tvContent.minHeight = portraitSize
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context!!.resources.getDimension(R.dimen.font_size))
        tvContent.gravity = Gravity.CENTER_VERTICAL
        tvContent.setPadding(context!!.resources.getDimension(R.dimen.edit_padding).toInt())
        //
        if(msgContent is TextMessage){
            tvContent.text = msgContent.content
        }
        // is sender
        LogUtil.i(TAG,"${msg.senderUserId},${ShareUtil.getString(Global.USER_ID, "")}")
        if (msg.senderUserId == ShareUtil.getString(Global.USER_ID, "")) {
            holder.itemView.ll_content.gravity = Gravity.RIGHT
            tvContent.setBackgroundResource(R.drawable.shape_chat)

            holder.itemView.ll_content.addView(tvContent)
            holder.itemView.ll_content.addView(ivPortrait)

        } else {
            holder.itemView.ll_content.gravity = Gravity.LEFT
            tvContent.setBackgroundResource(R.drawable.shape_white)

            holder.itemView.ll_content.addView(ivPortrait)
            holder.itemView.ll_content.addView(tvContent)
        }

    }

    override fun getLayout(): Int {
        return R.layout.item_chat
    }
}
