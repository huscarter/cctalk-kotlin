package com.whh.cctalk.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.adapter.ChatAdapter
import com.whh.cctalk.business.ChatBusiness
import com.whh.cctalk.util.CommonUtil
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.activity_chat.*

/**
 * The chat page
 */
class ChatActivity : BaseActivity() {
    private  val chatBusiness = ChatBusiness()
    private var adapter:ChatAdapter? = null
    private val msgList = ArrayList<Message>()
    private var targetId:String? = null

    override fun getLayout(): Int {
        return R.layout.activity_chat
    }

    override fun initData() {
        TAG = ChatActivity::class.java.simpleName

        adapter = ChatAdapter(context,msgList)

        targetId = intent.extras?.getString(Global.TARGET_ID)

        app_bar.setTitle(CommonUtil.secretPhone(targetId))
    }

    override fun initView() {

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        rv.adapter = adapter
        //
        chatBusiness.getLocalMessage(Conversation.ConversationType.PRIVATE,targetId,0,::afterGetList)
        //
        btn_send.setOnClickListener {
            val content = et_content.text.trim()
            if(!CommonUtil.isEmpty(content)){
                val msg = Message.obtain(targetId,Conversation.ConversationType.PRIVATE,TextMessage.obtain(content.toString()))
                chatBusiness.sendMessage(msg,::afterSendMessage)
            }
        }
        //
        refresh_layout.setOnRefreshListener {
            val messageId = if(msgList.size>0){
                msgList[msgList.size-1].messageId
            }else{
                0
            }
            chatBusiness.getLocalMessage(Conversation.ConversationType.PRIVATE,targetId,messageId,::afterGetList)
        }
    }

    private fun afterSendMessage(msg:Message?){
        if(msg!=null){
            //
            et_content.text.clear()
            //
            msgList.add(0,msg)
            adapter?.notifyDataSetChanged()

            rv.scrollToPosition(0)
        }
    }

    private fun afterGetList(list:List<Message>?){
        if(list!=null){
            msgList.addAll(list)
        }
        adapter?.notifyDataSetChanged()

        if(refresh_layout.isRefreshing){
            refresh_layout.finishRefresh()
        }else{
            // 滑动到最底部，因为reverse了所以定位到0就行
            rv.scrollToPosition(0)
        }
    }

}
