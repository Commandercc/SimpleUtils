package com.ccc.simpleutils.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    //获取当前时间 格式： yyyy-MM-dd HH:mm:ss
    fun getCurrentTime(): String? {
        val date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }
}