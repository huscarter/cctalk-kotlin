package com.whh.cctalk.business

import com.whh.cctalk.util.LogUtil
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation

/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明: The business of chat
 * <p>
 */
class ChatBusiness : BaseBusiness() {

    //
    companion object {
        val TAG: String = ChatBusiness::class.java.simpleName
    }

    /**
     * To get the conversion list
     */
    fun sendMessage(timeStamp: Long, pageSize: Int, callback: (list: List<Conversation>) -> Unit) {
        RongIMClient.getInstance()
            .getConversationListByPage(
                object : RongIMClient.ResultCallback<List<Conversation>>() {
                    override fun onSuccess(list: List<Conversation>?) {
                        LogUtil.i(TAG, "onSuccess")
                        if (list != null) {
                            LogUtil.i(TAG, "list size:${list.size}")
                            callback(list)
                        }
                    }

                    override fun onError(error: RongIMClient.ErrorCode?) {
                        LogUtil.i(TAG, "error:$error")
                    }

                },
                timeStamp,
                pageSize,
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP
            )
    }
}