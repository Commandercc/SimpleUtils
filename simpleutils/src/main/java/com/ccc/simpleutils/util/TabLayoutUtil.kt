package com.ccc.simpleutils.util

import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout

object TabLayoutUtil {
    /**
     * 通过反射改变 tab 下标线 的长度
     */
    fun setTabWidth(tabLayout: TabLayout, padding: Int) {
        tabLayout.post {
            try {
                //拿到tabLayout的mTabStrip属性
                val slidingTabIndicator = tabLayout.getChildAt(0) as LinearLayout
                for (i in 0 until slidingTabIndicator.childCount) {
                    val tabView = slidingTabIndicator.getChildAt(i)

                    //拿到tabView的mTextView属性  tab的字数不固定 一定用反射取mTextView
                    val mTextViewField = tabView.javaClass.getDeclaredField("textView")
                    mTextViewField.isAccessible = true
                    val mTextView = mTextViewField[tabView] as TextView
                    tabView.setPadding(0, 0, 0, 0)

                    //想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    var width = 0
                    width = mTextView.width
                    if (width == 0) {
                        mTextView.measure(0, 0)
                        width = mTextView.measuredWidth
                    }

                    //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    val params = tabView.layoutParams as LinearLayout.LayoutParams
                    params.width = width
                    params.leftMargin = padding
                    params.rightMargin = padding
                    tabView.layoutParams = params
                    tabView.invalidate()
                }
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }
}
