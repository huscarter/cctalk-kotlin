package com.whh.cctalk.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whh.cctalk.R

/**
 * Create by huscarter@163.com on 2020/6/29
 * <p>
 * 类说明:
 * <p>
 */
class CommonDivide : RecyclerView.ItemDecoration {

    private var divider: Drawable

    private var orientation = LinearLayoutManager.VERTICAL

    constructor(
        context: Context?,
        orientation: Int
    ) {
        divider = ColorDrawable(context?.resources?.getColor(R.color.divide)!!)
        setOrientation(orientation)
    }

    fun setDivide(drawable: Drawable) {
        this.divider = divider
    }

    private fun setOrientation(orientation: Int) {
        require(!(orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL)) { "invalid orientation" }
        this.orientation = orientation
    }

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val v = RecyclerView(parent.context)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect[0, 0, 0] = divider.intrinsicHeight
        } else {
            outRect[0, 0, divider.intrinsicWidth] = 0
        }
    }

}