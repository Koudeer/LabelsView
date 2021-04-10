package com.koudeer.labels

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup

class LabelsView : ViewGroup {

    private var mWordMargin: Int = 0;//文本控件间隔
    private var mLineMargin: Int = 0;//行距
    private var selectedBackgroundColor: Int = 0;//选中背景颜色
    private var unSelectedBackgroundColor: Int = 0;//未选中背景颜色
    private var selectedTextColor: Int = 0;//文本选中颜色
    private var unSelectedTextColor: Int = 0;//文本未选中颜色

    private val defaultSelectedBgColor = 0x2196F3 //默认选中背景颜色
    private val defaultUnSelectedBgColor = 0xfff //默认未选中背景颜色
    private val defaultSelectTextColor = 0x000 //未选中文本颜色
    private val defaultSelectedTextColor = 0xfff //选中文本颜色

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
    }

    /**
     * 获取属性
     */
    private fun getAttrs(context: Context?, attrs: AttributeSet?) {

        val typeArray: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.LabelsView)

        typeArray?.apply {
            selectedBackgroundColor = getColor(R.styleable.LabelsView_selectedBackgroundColor,defaultSelectedBgColor)
            unSelectedBackgroundColor = getColor(R.styleable.LabelsView_unSelectedBackgroundColor,defaultUnSelectedBgColor)
            selectedTextColor = getColor(R.styleable.LabelsView_selectedTextColor,defaultSelectTextColor)
            unSelectedTextColor = getColor(R.styleable.LabelsView_unSelectedTextColor,defaultSelectedTextColor)
            mWordMargin = getDimensionPixelOffset(R.styleable.LabelsView_WordMargin,5.dp2px.toInt())
            mLineMargin = getDimensionPixelOffset(R.styleable.LabelsView_LineMargin,5.dp2px.toInt())
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    /**
     * 添加控件
     */
    private fun addLabel() {

    }
}