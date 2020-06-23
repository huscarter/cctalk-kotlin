package com.whh.cctalk.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.whh.cctalk.R

/**
 * Create by huscarter@163.com on 2020/6/16
 * <p>
 * 类说明: Loading view
 */
class LoadingDialog(context: Context) : Dialog(context) {
    var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        val ll = LinearLayout(context)
        ll.layoutParams = ViewGroup.LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.loading_size),
            context.resources.getDimensionPixelSize(R.dimen.loading_size)
        )
        ll.orientation = LinearLayout.VERTICAL
        ll.gravity = Gravity.CENTER

        val progressBar = ProgressBar(
            context,
            null,
            android.R.style.Widget_DeviceDefault_Light_ProgressBar_Inverse
        )
        ll.addView(progressBar)

        if (content != null) {
            val tv = TextView(context)
            tv.setTextColor(context.resources.getColor(R.color.text_main))
            tv.textSize = context.resources.getDimension(R.dimen.font_size)
            tv.text = content
        }
    }

}
