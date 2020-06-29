package com.whh.cctalk.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by huscarter@163.com on 2020/6/29
 * <p>
 * 类说明:
 * <p>
 */
object DateUtil {
    /**
     *
     */
    fun format(date: Date,format:String):String{
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }
}