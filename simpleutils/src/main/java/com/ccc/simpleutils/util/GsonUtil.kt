package com.ccc.simpleutils.util

import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*

/**
 * Gson工具类
 */
object GsonUtil {
    private val gson = Gson()

    /**
     * 解析json
     * @param json json字符串
     * @param clazz class
     * @return 对象
     */
    fun <T> toObject(json: String?, clazz: Class<T>?): T {
        return gson.fromJson(json, clazz)
    }

    /**
     *
     * @param json json字符串
     * @param type Type
     * @return 对象
     */
    fun <T> toObject(json: String?, type: Type?): T {
        return gson.fromJson(json, type)
    }

    /**
     * 解析json数组
     * @param json json字符串
     * @param clazz 示例 T[].class
     * @return 集合
     */
    fun <T> toList(json: String?, clazz: Class<Array<T>?>?): List<T> {
        val array = gson.fromJson(json, clazz)!!
        return Arrays.asList(*array)
    }

    /**
     * 将Object转为json
     * @param src Object
     * @return json字符串
     */
    fun toJson(src: Any?): String {
        return gson.toJson(src)
    }
}