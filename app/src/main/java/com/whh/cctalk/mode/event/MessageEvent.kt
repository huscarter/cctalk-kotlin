package com.whh.cctalk.mode.event

import io.rong.imlib.model.Message

/**
 * Create by huscarter@163.com on 2020/6/24
 * <p>
 * 类说明: The event of message
 * <p>
 */
class MessageEvent {
    var message: Message? = null
    var left: Int = 0
    var hasPackage: Boolean = false
    var offline: Boolean = false

    constructor(message: Message?, left: Int, hasPackage: Boolean, offline: Boolean){
        this.message = message
        this.left = left
        this.hasPackage = hasPackage
        this.offline = offline
    }
}