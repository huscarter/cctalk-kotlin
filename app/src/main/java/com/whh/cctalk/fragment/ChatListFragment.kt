package com.whh.cctalk.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.whh.cctalk.Global
import com.whh.cctalk.R
import com.whh.cctalk.adapter.ChatListAdapter
import com.whh.cctalk.business.ChatListBusiness
import com.whh.cctalk.mode.event.ConnectionEvent
import com.whh.cctalk.mode.event.MessageEvent
import com.whh.cctalk.util.LogUtil
import com.whh.cctalk.view.CommonDivide
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.fragment_chat_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * The message list page
 */
class ChatListFragment : BaseFragment() {

    private val chatBusiness = ChatListBusiness()

    private val chatList = ArrayList<Conversation>()
    private var adapter: ChatListAdapter? = null

    override fun getLayout(): Int {
        return R.layout.fragment_chat_list
    }

    override fun initData() {
        //
        TAG = ChatListFragment::class.java.simpleName
        //
        EventBus.getDefault().register(this)

        adapter = ChatListAdapter(context,chatList)
    }

    override fun initView() {
        app_bar.setNavigationVisible(false)

        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        rv.addItemDecoration(CommonDivide(context,LinearLayoutManager.VERTICAL))
        //
        refresh_layout.setOnRefreshListener {
            chatList.clear()
            chatBusiness.getChatList(0,Global.PAGE_SIZE,::afterGetList)
        }
        refresh_layout.setOnLoadMoreListener {
            var timestamp = 0L
            if(chatList.size>0){
                timestamp = chatList[chatList.size-1].sentTime
            }
            chatBusiness.getChatList(timestamp,Global.PAGE_SIZE,::afterGetList)
        }
    }

    private fun afterGetList(list:List<Conversation>?){
        if(list!=null){
            chatList.addAll(list)
        }
        adapter?.notifyDataSetChanged()

        if(refresh_layout.isLoading){
            refresh_layout.finishLoadMore()
        }else{
            refresh_layout.finishRefresh()
        }
        LogUtil.i(TAG,"afterGetChatList size:${chatList.size}")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceiveEvent(event:MessageEvent){
        LogUtil.i(TAG,"onMessageReceiveEvent:${event.left}")
        if(event.left==0){
            chatList.clear()
            chatBusiness.getChatList(0,Global.PAGE_SIZE,::afterGetList)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConnectEvent(event:ConnectionEvent){
        LogUtil.i(TAG,"onConnectEvent")
        if(event.status==RongIMClient.ErrorCode.CONNECTED){
            //
            app_bar.setTitle("chat list")
            //
            chatList.clear()
            chatBusiness.getChatList(0,Global.PAGE_SIZE,::afterGetList)
        }else{
            app_bar.setTitle("connect failed")
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
