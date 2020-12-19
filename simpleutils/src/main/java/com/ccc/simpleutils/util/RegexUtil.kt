package com.ccc.simpleutils.util

import com.blankj.utilcode.util.RegexUtils

/**
 *  正则匹配
 */
object RegexUtil {

    //手机号
    fun isPhoneNumber(value: String) = RegexUtils.isMobileSimple(value)

    //邮箱
    fun isEmailNumber(value: String) = RegexUtils.isEmail(value)

    //身份证
    fun isIdCardNumber(value: String) = RegexUtils.isIDCard18(value)

    //Ip地址
    fun isIpNumber(value: String) = RegexUtils.isIP(value)
}