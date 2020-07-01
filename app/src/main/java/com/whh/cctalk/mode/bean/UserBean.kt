package com.whh.cctalk.mode.bean

/**
 * Create by huscarter@163.com on 2020/6/15
 * <p>
 * 类说明: Im用户信息
 */
class UserBean() : BaseBean() {
    var userId: String? = null
    var token: String? = null
    var userName: String? = null
    var userPortrait: String? = null
    var sentTime: Long? = null

    constructor(userId:String) : this() {
        this.userId = userId
    }
}