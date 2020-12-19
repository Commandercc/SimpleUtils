package com.ccc.simpleutils.util


import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils

object KeyBoardUtil {

    //显示软键盘
    fun showSoftInput(view: View) {
        KeyboardUtils.showSoftInput(view)
    }

    //隐藏软键盘
    fun hideSoftInput(view: View) {
        KeyboardUtils.hideSoftInput(view)
    }

    //判断软键盘是否可见
    fun isSoftInputVisible(activity: Activity) = KeyboardUtils.isSoftInputVisible(activity)
}