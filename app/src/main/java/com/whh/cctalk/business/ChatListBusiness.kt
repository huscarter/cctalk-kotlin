package com.whh.cctalk.business

import android.content.Context
import android.content.Intent
import com.whh.cctalk.Global
import com.whh.cctalk.activity.LoginActivity
import com.whh.cctalk.fragment.BaseFragment
import com.whh.cctalk.mode.event.ConnectionEvent
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.util.ShareUtil
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import org.greenrobot.eventbus.EventBus

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
     * To get the im token
     */
    fun imConnect(context: Context?) {
        val token = ShareUtil.getString(Global.TOKEN, null)
        RongIMClient.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(userId: String?) {
                LogUtil.i(BaseFragment.TAG,"onSuccess userId:$userId")
                EventBus.getDefault().post(ConnectionEvent(RongIMClient.ErrorCode.CONNECTED))
            }

            /**
             * 31004	Token 无效
             * 31005	AppKey 与 Token 不否匹配
             * 30011	Socket 断开
             * 30002	连接不可用, 连接相关的错误码，SDK 会做好自动重连，开发者无须处理
             * -1	未知错误
             */
            override fun onError(err: RongIMClient.ErrorCode?) {
                LogUtil.i(BaseFragment.TAG,"onError $err")
                EventBus.getDefault().post(ConnectionEvent(err))
            }

            /**
             *  回调, 可能是 token 过期造成. 需要向服务器重新获取 Token 并建立连接
             */
            override fun onTokenIncorrect() {
                context?.startActivity(Intent(context, LoginActivity::class.java))
            }
        })
    }

    /**
     * To get the conversion list
     */
    fun getChatList(timeStamp: Long, pageSize: Int, callback: (list: List<Conversation>?) -> Unit) {
        LogUtil.i(TAG,"getChatList $timeStamp,$pageSize")
        RongIMClient.getInstance().getConversationListByPage(object : RongIMClient.ResultCallback<List<Conversation>>() {
                    override fun onSuccess(list: List<Conversation>?) {
                        LogUtil.i(TAG, "onSuccess list:$list,size:${list?.size}")
                        callback(list)
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