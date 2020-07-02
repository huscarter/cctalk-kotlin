package com.whh.cctalk.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toolbar
import com.whh.cctalk.R
import com.whh.cctalk.util.LogUtil
import kotlinx.android.synthetic.main.view_toolbar.view.*

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明: The tool bar of app.
 * <p>
 */
class AppToolBar : LinearLayout {
    companion  object{
        private val TAG = AppToolBar::class.java.simpleName
    }

    /**
     *
     */
    constructor(context: Context?) : super(context) {
        //
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initView(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context, attrs)
    }

    /**
     *
     */
    private fun initView(context: Context?, attrs: AttributeSet?) {
        this.orientation = LinearLayout.VERTICAL

        // add toolbar
        val toolbar = LayoutInflater.from(context).inflate(R.layout.view_toolbar, null)
        toolbar.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            context!!.resources.getDimension(R.dimen.title_height_inner).toInt()
        )
        addView(toolbar)


        // add divide
        val divide = LayoutInflater.from(context).inflate(R.layout.view_divide, null)
//        divide.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            context!!.resources.getDimension(R.dimen.divide).toInt()
//        )
        addView(divide)

        // set attrs
        if (attrs != null) {
            val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.AppToolBar)
            //
            tv_title.text = typedArray?.getString(R.styleable.AppToolBar_title)
            //
            divide.setBackgroundColor(typedArray?.getColor(R.styleable.AppToolBar_divide_color,context?.resources.getColor(R.color.divide))!!)

            setNavigationVisible(typedArray?.getBoolean(R.styleable.AppToolBar_navigationVisible,true))
        }

        this.toolbar.navigationIcon?.setTint(context?.resources.getColor(R.color.colorPrimary))

    }

    fun setTitle(title:String?){
        tv_title.text = title?:""
    }

    fun getTitle():String{
        return tv_title.text.toString()
    }

    fun setNavigationVisible(visible:Boolean){
        if(visible){
            this.toolbar.navigationIcon = context.resources.getDrawable(R.drawable.prev)
        }else{
            this.toolbar.navigationIcon = null
        }
    }

    fun setNavigationListener(listener: View.OnClickListener){
        this.toolbar.setNavigationOnClickListener(listener)
    }

}