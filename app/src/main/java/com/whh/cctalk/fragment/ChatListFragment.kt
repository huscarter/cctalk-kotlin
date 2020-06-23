package com.whh.cctalk.fragment

import android.content.Context
import android.content.Intent
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.activity.LoginActivity
import com.whh.cctalk.adapter.ChatListAdapter
import com.whh.cctalk.business.ChatListBusiness
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.util.ShareUtil
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.fragment_chat_list.*

/**
 * The message list page
 */
class ChatListFragment : BaseFragment() {

    val chatBusiness = ChatListBusiness()

    val chatList = ArrayList<Conversation>()
    val adapter: ChatListAdapter = ChatListAdapter(chatList)

    override fun getLayout(): Int {
        return R.layout.fragment_chat_list
    }

    override fun initData() {
        //
        imConnect(context)
        //
    }

    override fun initView() {
        //
    }

    /**
     * To get the im token
     */
    private fun imConnect(context: Context?) {
        val token = ShareUtil.getString(Global.TOKEN, null)
        RongIMClient.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(userId: String?) {
                LogUtil.i(TAG,"onSuccess userId:$userId")
                //
                app_bar.setTitle("chat list")
                // To get chat list
                chatBusiness.getChatList {
                    chatList.clear()
                    chatList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

            /**
             * 31004	Token 无效
             * 31005	AppKey 与 Token 不否匹配
             * 30011	Socket 断开
             * 30002	连接不可用, 连接相关的错误码，SDK 会做好自动重连，开发者无须处理
             * -1	未知错误
             */
            override fun onError(err: RongIMClient.ErrorCode?) {
                //
            }

            /**
             *  回调, 可能是 token 过期造成. 需要向服务器重新获取 Token 并建立连接
             */
            override fun onTokenIncorrect() {
                context?.startActivity(Intent(context, LoginActivity::class.java))
            }
        })
    }

}
