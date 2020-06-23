package com.whh.cctalk.util

import java.security.MessageDigest

/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明:
 * <p>
 */
object EncodeUtil {
    val TAG: String = EncodeUtil::class.java.simpleName

    /**
     *
     */
    fun sha1(data: String): String {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-1")
        val encodeArray = digest.digest(data.toByteArray())
        //
        val hexBuffer = StringBuffer()
        for (i in encodeArray) {
            val temp = i.toInt() and (0xff)
            val hexTemp = Integer.toHexString(temp)
            if (hexTemp.length == 1) {
                hexBuffer.append("0")
            }
            hexBuffer.append(hexTemp)
        }
        return hexBuffer.toString()
    }

}