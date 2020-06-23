package com.whh.cctalk.business

import com.whh.cctalk.util.LogUtil
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation

/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明: The business of chat list
 * <p>
 */
class ChatListBusiness : BaseBusiness() {
    //
    companion object {
        val TAG: String = ChatListBusiness::class.java.simpleName
    }

    /**
     * To get the conversion list
     */
    fun getChatList(callback: (list: List<Conversation>) -> Unit) {
        RongIMClient.getInstance()
            .getConversationList(object : RongIMClient.ResultCallback<List<Conversation>>() {
                override fun onSuccess(list: List<Conversation>?) {
                    LogUtil.i(TAG,"onSuccess")
                    if (list != null) {
                        LogUtil.i(TAG,"list size:${list.size}")
                        callback(list)
                    }
                }

                override fun onError(error: RongIMClient.ErrorCode?) {
                    LogUtil.i(TAG,"error:$error")
                }

            }, Conversation.ConversationType.PRIVATE)
    }
}