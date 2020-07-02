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
        return if(isSameDay(Date(),Date(time))){
            val dateFormat = SimpleDateFormat("HH:mm")
            dateFormat.format(time)
        }else{
            if(isSameYear(Date(),Date(time))){
                val dateFormat = SimpleDateFormat("MM/dd HH:mm")
                dateFormat.format(time)
            }else{
                val dateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm")
                dateFormat.format(time)
            }
        }

    }

    fun isSameDay(day0:Date,day1:Date):Boolean{
        val calendar0 = Calendar.getInstance()
        calendar0.time = day0
        val calendar1 = Calendar.getInstance()
        calendar1.time = day1

        return calendar0.get(Calendar.DAY_OF_MONTH)==calendar1.get(Calendar.DAY_OF_MONTH)
    }

    fun isSameYear(day0:Date,day1:Date):Boolean{
        val calendar0 = Calendar.getInstance()
        calendar0.time = day0
        val calendar1 = Calendar.getInstance()
        calendar1.time = day1

        return calendar0.get(Calendar.YEAR)==calendar1.get(Calendar.YEAR)
    }


}