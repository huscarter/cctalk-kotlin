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
    fun format(date: Date?,format:String):String{
        if(date==null){
            return ""
        }
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }

    fun format(date: Date?):String{
        if(date==null){
            return ""
        }
        val dateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm")
        return dateFormat.format(date)
    }

    fun  formatChatTime(time: Long?):String{
        if(time==null){
            return ""
        }
        val nowTime = Date().time

        return if(nowTime-time<24*60*60*1000){
            val dateFormat = SimpleDateFormat("HH:mm")
            dateFormat.format(time)
        }else{
            val dateFormat = SimpleDateFormat("YYYY")
            if(dateFormat.format(Date())==dateFormat.format(Date(time))){
                val dateFormat = SimpleDateFormat("MM/dd HH:mm")
                dateFormat.format(time)
            }else{
                val dateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm")
                dateFormat.format(time)
            }
        }

    }


}