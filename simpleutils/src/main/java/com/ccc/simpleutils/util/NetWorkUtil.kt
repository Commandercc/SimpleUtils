package com.ccc.simpleutils.util

import com.blankj.utilcode.util.NetworkUtils

/**
 *  简单网络工具
 */
object NetWorkUtil {

    //判断网络是否连接
    fun isConnected() = NetworkUtils.isConnected()

    //判断网络是否是移动数据
    fun isMobileData() = NetworkUtils.isMobileData()

    //判断wifi是否连接
    fun isWifiConnected() = NetworkUtils.isWifiConnected()
}