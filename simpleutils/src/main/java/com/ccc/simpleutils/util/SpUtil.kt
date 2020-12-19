package com.ccc.simpleutils.util

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class SpUtil private constructor(context: Context, name: String) {

    init {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    companion object {
        private var instance: SpUtil? = null
        private lateinit var sp: SharedPreferences

        /**
         * 初始化SharedPreferencesUtil,只需要初始化一次，建议在Application中初始化
         * @param context 上下文对象
         * @param name    SharedPreferences Name
         */
        fun getInstance(context: Context, name: String) {
            if (instance == null) {
                instance = SpUtil(context, name)
            }
        }

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         */
        fun putValue(key: String?, value: Any) {
            val editor = sp.edit()
            if (value is String) {
                editor.putString(key, value)
            } else if (value is Int) {
                editor.putInt(key, value)
            } else if (value is Boolean) {
                editor.putBoolean(key, value)
            } else if (value is Float) {
                editor.putFloat(key, value)
            } else if (value is Long) {
                editor.putLong(key, value)
            } else {
                editor.putString(key, value.toString())
            }
            SharedPreferencesCompat.apply(editor)
        }

        /***
         * 批量存储数据的方法
         * @param map
         */
        fun putManyValue(map: Map<String, Any>) {
            val editor = sp.edit()
            // 循环遍历map
            for ((key, value) in map) {
                // 获取键
                // 获取值
                // 判断值的类型然后存储
                if (value is String) {
                    editor.putString(key, value)
                } else if (value is Int) {
                    editor.putInt(key, (value))
                } else if (value is Boolean) {
                    editor.putBoolean(key, (value))
                } else if (value is Float) {
                    editor.putFloat(key, (value))
                } else if (value is Long) {
                    editor.putLong(key, (value))
                } else {
                    editor.putString(key, value.toString())
                }
            }
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         *
         * @param key
         * @param defaultObject
         * @return
         */
        operator fun getValue(key: String?, defaultObject: Any?): Any? {
            if (defaultObject is String) {
                return sp.getString(key, defaultObject as String?)
            } else if (defaultObject is Int) {
                return sp.getInt(key, (defaultObject as Int?)!!)
            } else if (defaultObject is Boolean) {
                return sp.getBoolean(key, (defaultObject as Boolean?)!!)
            } else if (defaultObject is Float) {
                return sp.getFloat(key, (defaultObject as Float?)!!)
            } else if (defaultObject is Long) {
                return sp.getLong(key, (defaultObject as Long?)!!)
            }
            return null
        }

        /**
         * 移除某个key值已经对应的值
         *
         * @param key
         */
        fun remove(key: String?) {
            val editor = sp.edit()
            editor.remove(key)
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 清除所有数据
         */
        fun clearAll() {
            val editor = sp.edit()
            editor.clear()
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        operator fun contains(key: String?): Boolean {
            return sp.contains(key)
        }

        /**
         * 返回所有的键值对
         *
         * @return
         */
        val all: Map<String, *>
            get() = sp.all
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * 里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
     * 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
     * 所以我们使用apply进行替代，apply异步的进行写入；
     * 由于在apply是在API 9以后引入，所以有了这个兼容；
     */

    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        //反射查找apply的方法
        private fun findApplyMethod(): Method? {
            try {
                val clz: Class<*> = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }
            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }
            editor.commit()
        }
    }
}
