package com.whh.cctalk.util

import android.content.Context
import android.content.SharedPreferences


/**
 * Create by huscarter@163.com on 2020/6/13
 * <p>
 * 类说明: The SharedPreference util<BR/>
 */
object ShareUtil {
    private var instance: SharedPreferences? = null
    private const val SHARED_NAME = "cctalk"

    fun init(context: Context) {
        instance = context.getSharedPreferences(
            SHARED_NAME, Context.MODE_PRIVATE)
    }

    fun getInstance(): SharedPreferences? {
        return instance
    }

    /**
     * 保存Sring型数据
     *
     * @param key   键
     * @param value 数值
     */
    fun setString(key: String?, value: String?) {
        val editor: SharedPreferences.Editor? = instance?.edit()
        editor?.putString(key, value)
        editor?.commit()
    }

    /**
     * 保存int型数据
     *
     * @param key   键
     * @param value 数值
     */
    fun setInt(key: String?, value: Int) {
        val editor: SharedPreferences.Editor? = instance?.edit()
        editor?.putInt(key, value)
        editor?.commit()
    }

    /**
     * 保存int型数据
     *
     * @param key   键
     * @param value 数值
     */
    fun setLong(key: String?, value: Long?) {
        val editor: SharedPreferences.Editor? = instance?.edit()
        editor?.putLong(key, value!!)
        editor?.commit()
    }

    /**
     * 保存Boolean型数据
     *
     * @param key   键
     * @param value 数值
     */
    fun setBoolean(key: String?, value: Boolean) {
        val editor: SharedPreferences.Editor? = instance?.edit()
        editor?.putBoolean(key, value)
        editor?.commit()
    }

    /**
     * 获取String类型数据
     *
     * @param key          键
     * @param default 数值
     * @return String类型数据
     */
    fun getString(key: String?, default: String?): String? {
        return if (instance == null) {
            ""
        } else instance?.getString(key, default)
    }

    /**
     * 获取Int类型数据
     *
     * @param key          键
     * @param default
     * @return Int类型数据
     */
    fun getInt(key: String?, default: Int): Int? {
        return instance?.getInt(key, default)
    }

    fun getLong(key: String?, default: Long): Long? {
        return instance?.getLong(key, default)
    }

    /**
     * 获取Boolean类型数据
     *
     * @param key          键
     * @param default
     * @return Boolean类型数据
     */
    fun getBoolean(key: String?, default: Boolean): Boolean? {
        return instance?.getBoolean(key, default)
    }

    /**
     * 除清首选项数据
     */
    fun clearData() {
        instance?.edit()?.clear()?.commit()
    }

    /**
     * 是否存在key键值
     *
     * @param key 键
     * @return 是否存在
     */
    fun contains(key: String?): Boolean? {
        return instance?.contains(key)
    }

}
