package com.whh.cctalk.business

import com.whh.cctalk.util.LogUtil
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message


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
        private const val MESSAGE_PAGE_SIZE = 50
    }

    /**
     * To get send message
     */
    fun sendMessage(message: Message,callback: (message: Message?) -> Unit) {
        RongIMClient.getInstance().sendMessage(message,null, null,object : IRongCallback.ISendMessageCallback{
            override fun onAttached(msg: Message?) {

            }

            override fun onSuccess(msg: Message?) {
                callback(msg)
            }

            override fun onError(msg: Message?, error: RongIMClient.ErrorCode?) {
                callback(null)
            }
        })
    }

    /**
     * To get local message
     */
    fun getLocalMessage(conversationType:Conversation.ConversationType,targetId:String?,lastMessageId:Int,callback: (list: List<Message>?) -> Unit){
        RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, lastMessageId, MESSAGE_PAGE_SIZE,object : RongIMClient.ResultCallback<List<Message>>() {
                    /**
                     * 成功时回调
                     * @param messages 获取的消息列表
                     */
                    override fun onSuccess(messages: List<Message>) {
                        callback(messages)
                    }

                    /**
                     * 错误时回调。
                     * @param errorCode 错误码
                     */
                    override fun onError(errorCode: RongIMClient.ErrorCode) {
                        callback(null)
                    }
                })

    }

    /**
     * To get remote message
     */
    fun getRemoteMessage(conversationType:Conversation.ConversationType,targetId:String?,sendTime:Long,callback: (list: List<Message>?) -> Unit){
        RongIMClient.getInstance().getRemoteHistoryMessages(conversationType, targetId, sendTime, MESSAGE_PAGE_SIZE,object : RongIMClient.ResultCallback<List<Message>>() {
            /**
             * 成功时回调
             * @param messages 获取的消息列表
             */
            override fun onSuccess(messages: List<Message>) {
                callback(messages)
            }

            /**
             * 错误时回调。
             * @param errorCode 错误码
             */
            override fun onError(errorCode: RongIMClient.ErrorCode) {
                callback(null)
            }
        })

    }
}