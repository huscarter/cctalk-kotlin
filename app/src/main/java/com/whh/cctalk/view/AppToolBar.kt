package com.whh.cctalk.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.whh.cctalk.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明: The tool bar of app.
 * <p>
 */
class AppToolBar : LinearLayout {
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

    private fun initView(context: Context?, attrs: AttributeSet?) {
        this.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.orientation = LinearLayout.VERTICAL

        val toolbar = LayoutInflater.from(context).inflate(R.layout.view_toolbar, null)
        addView(toolbar)

        if (attrs != null) {
            val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.AppToolBar)
            tv_title.text = typedArray?.getString(R.styleable.AppToolBar_title)
        }

        val divide = LayoutInflater.from(context).inflate(R.layout.view_divide, null)
        addView(divide)
    }

    fun setTitle(title:String){
        tv_title.text = title
    }

    fun getTitle():String{
        return tv_title.text.toString()
    }

}