package com.whh.cctalk.util

import android.util.Log
import com.whh.cctalk.BuildConfig

/**
 * Create by huscarter@163.com on 2020/6/11
 * <p>
 * 类说明:The log util
 */
object LogUtil {
    private const val NULL = -1

    fun v(tag: String, msg: String): Int {
        if (BuildConfig.DEBUG) {
            return Log.v(tag, msg)
        }
        return NULL
    }

    fun i(tag: String, msg: String): Int {
        if (BuildConfig.DEBUG) {
            return Log.i(tag, msg)
        }
        return NULL
    }

    fun d(tag: String, msg: String): Int {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
        return NULL
    }

    fun e(tag: String, msg: String): Int {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
        return NULL
    }
}