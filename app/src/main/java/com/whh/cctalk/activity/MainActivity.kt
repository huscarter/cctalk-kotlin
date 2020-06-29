package com.whh.cctalk.activity

import android.graphics.Point
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.whh.cctalk.R
import com.whh.cctalk.business.ChatListBusiness
import com.whh.cctalk.fragment.ContractListFragment
import com.whh.cctalk.fragment.ChatListFragment
import com.whh.cctalk.fragment.PersonalFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The main page
 */
class MainActivity : BaseActivity() {

    private val tabTitles = arrayOf("Chat List", "Contract List", "Personal")
    private val tabContents = arrayOf(
        ChatListFragment::class.java,
        ContractListFragment::class.java,
        PersonalFragment::class.java
    )
    private val tabIndicators = arrayOfNulls<TextView>(tabTitles.size)
    private val point = Point()

    private var currentIndex = 0

    private val chatBusiness = ChatListBusiness()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        TAG = MainActivity::class.java.simpleName
        windowManager.defaultDisplay.getSize(point)

        //
        chatBusiness.imConnect(context)
    }

    override fun initView() {
        //
        tab.setup(this, supportFragmentManager, R.id.fl_content)
        //
        for (i in tabTitles.indices) {
            val spec = tab.newTabSpec(tabTitles[i]).setIndicator(getIndicator(tabTitles[i], i))
            tab.addTab(spec, tabContents[i], null)
        }

        tab.setOnTabChangedListener {
            for (i in tabTitles.indices) {
                if (it == tabIndicators[i]?.text) {
                    tabIndicators[i]?.setTextColor(resources.getColor(R.color.colorPrimary))
                    currentIndex = i
                } else {
                    tabIndicators[i]?.setTextColor(resources.getColor(R.color.text_main))
                }
            }
        }
    }

    private fun getIndicator(title: String, index: Int): View {
        val tv = TextView(this)
        tv.layoutParams = FrameLayout.LayoutParams(
            point.x / tabTitles.size,
            R.dimen.item_height
        )
        tv.gravity = Gravity.CENTER
        if (index == currentIndex) {
            tv?.setTextColor(resources.getColor(R.color.colorPrimary))
        } else {
            tv?.setTextColor(resources.getColor(R.color.text_main))
        }
        tv.text = title
        //
        tabIndicators[index] = tv
        return tv
    }

}
