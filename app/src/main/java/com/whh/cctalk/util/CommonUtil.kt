package com.whh.cctalk.util

import android.content.Context
import android.widget.Toast

/**
 * Create by huscarter@163.com on 2020/6/13
 *
 * 类说明: The common util
 */
object CommonUtil {

    fun isEmpty(obj: Any?): Boolean {
        return obj == null || obj == ""
    }

    fun showToast(context: Context,content:String){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
    }
}