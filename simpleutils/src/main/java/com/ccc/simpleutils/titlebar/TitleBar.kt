package com.ccc.simpleutils.titlebar

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.KeyboardUtils
import com.ccc.coreutil.R

/**
 *  自定义顶部 toolbar
 */
class TitleBar @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(mContext, attrs, defStyleAttr), View.OnClickListener {
    private lateinit var mTitleTv: TextView
    private lateinit var mImgBack: ImageView
    private lateinit var mImgRight: ImageView
    private lateinit var mTxtRight: TextView
    private var titleName: String? = null
    private var backImageVisible: Boolean? = null

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        titleName = attr.getString(R.styleable.TitleBar_titleName)
        backImageVisible = attr.getBoolean(R.styleable.TitleBar_backImageVisible, true)
        attr.recycle()
    }

    /*
        初始化布局
     */
    private fun initView() {
        View.inflate(mContext, R.layout.my_titlebar, this)
        //返回操作
        mImgBack = findViewById(R.id.imgBack)
        //标题
        mTitleTv = findViewById(R.id.txtTitle)
        //右图
        mImgRight = findViewById(R.id.imgRight)
        //右文字
        mTxtRight = findViewById(R.id.txtRight)

        mImgBack.setOnClickListener(this)
        mTitleTv.text = titleName ?: ""
        setBackImageVisible(backImageVisible!!)
    }

    /**
     *  返回按钮是否显示
     */
    private fun setBackImageVisible(visibility: Boolean) {
        when (visibility) {
            true -> mImgBack.visibility = View.VISIBLE
            false -> mImgBack.visibility = View.GONE
        }
    }

    /**
     *  设置标题
     */
    fun setTitle(title: String?) {
        mTitleTv.text = title
    }

    /**
     * 设置返回按钮图片
     */
    fun setBackImage(imageId: Int) {
        if (imageId != 0) {
            mImgBack.setImageResource(imageId)
        }
    }

    /**
     * 设置右边图片
     */
    fun setRightImage(imageId: Int) {
        require(mTxtRight.visibility != View.VISIBLE) { "文字和图片不可同时设置" }
        mImgRight.visibility = View.VISIBLE
        mImgRight.setImageResource(imageId)
    }

    /**
     * 设置右边文字
     */
    fun setRightText(text: String?) {
        if (text != null) {
            require(mImgRight.visibility != View.VISIBLE) { "文字和图片不可同时设置" }
            mTxtRight.visibility = View.VISIBLE
            mTxtRight.text = text
        }
    }

    /**
     * 设置右边文字背景
     */
    fun setRightBackColor(color: Int) {
        mTxtRight.setBackgroundColor(color)
    }

    /**
     * 控制右边文字是否可点击
     */
    fun setRightTextClick(click: Boolean) {
        mTxtRight.isClickable = click
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBack -> {
                if (KeyboardUtils.isSoftInputVisible(mContext as Activity)) {
                    KeyboardUtils.hideSoftInput(this)
                }
                mContext.finish()
            }
        }
    }

    fun setRightTextOnClickListener(onClickListener: OnClickListener) {
        mTxtRight.setOnClickListener(onClickListener)
    }

    fun setRightImgOnClickListener(onClickListener: OnClickListener) {
        mImgRight.setOnClickListener(onClickListener)
    }

    fun setTitleOnClickListener(onClickListener: OnClickListener) {
        mTitleTv.setOnClickListener(onClickListener)
    }

    init {
        initView()
    }
}