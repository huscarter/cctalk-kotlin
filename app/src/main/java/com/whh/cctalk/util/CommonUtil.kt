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

    /**
     * @param phone 13456729970
     * @return 134****9970
     */
    fun secretPhone(phone: String?): String? {
        if (isEmpty(phone)) {
            return ""
        }
        return if (phone!!.length < 10) {
            phone
        } else {
            phone!!.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")
        }
    }


}