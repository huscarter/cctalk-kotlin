package com.whh.cctalk

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The main page
 */
class MainActivity : AppCompatActivity() {

    private val tabTitles = arrayOf("Message List","Contract List","Personal")
    private val tabContents = arrayOf(MessageListFragment::class.java,ContractListFragment::class.java,PersonalFragment::class.java)
    private val tabIndicators = arrayOfNulls<TextView>(tabTitles.size)
    private val point = Point()

    private var currentIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initData(){
        windowManager.defaultDisplay.getSize(point)
    }

    private fun initView(){
        //
        tab.setup(this,supportFragmentManager,R.id.fl_content)
        //
        for(i in tabTitles.indices){
            val spec = tab.newTabSpec(tabTitles[i]).setIndicator(getIndicator(tabTitles[i],i))
            tab.addTab(spec,tabContents[i],null)
        }

        tab.setOnTabChangedListener {
            for(i in tabTitles.indices){
                if(it == tabIndicators[i]?.text){
                    tabIndicators[i]?.setTextColor(resources.getColor(R.color.colorPrimary))
                    currentIndex = i
                }else{
                    tabIndicators[i]?.setTextColor(resources.getColor(R.color.black))
                }
            }
        }
    }

    private fun getIndicator(title:String,index:Int):View{
        val tv = TextView(this)
        tv.layoutParams = FrameLayout.LayoutParams(point.x/tabTitles.size,R.dimen.item_height)
        tv.gravity = Gravity.CENTER
        if(index == currentIndex){
            tv?.setTextColor(resources.getColor(R.color.colorPrimary))
        }else{
            tv?.setTextColor(resources.getColor(R.color.black))
        }
        tv.text = title
        //
        tabIndicators[index] = tv
        return tv
    }

}
