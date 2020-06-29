package com.whh.cctalk.mode.event

import io.rong.imlib.RongIMClient

/**
 * Create by huscarter@163.com on 2020/6/24
 * <p>
 * 类说明: The event of message
 * <p>
 */
class ConnectionEvent {
    var status: RongIMClient.ErrorCode? = RongIMClient.ErrorCode.UNKNOWN

    constructor(status: RongIMClient.ErrorCode?) {
        this.status = status
    }
}