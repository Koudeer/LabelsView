package com.koudeer.labels

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LabelsView : ViewGroup, View.OnClickListener {

    //SparseIntArray
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

    private var mContext: Context? = null

    constructor(context: Context?) : super(context) {
        this.mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(context, attrs)
        this.mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttrs(context, attrs)
        this.mContext = context
    }

    /**
     * 获取属性
     */
    private fun getAttrs(context: Context?, attrs: AttributeSet?) {

        val typeArray: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.LabelsView)

        typeArray?.apply {
            selectedBackgroundColor =
                getColor(R.styleable.LabelsView_selectedBackgroundColor, defaultSelectedBgColor)
            unSelectedBackgroundColor =
                getColor(R.styleable.LabelsView_unSelectedBackgroundColor, defaultUnSelectedBgColor)
            selectedTextColor =
                getColor(R.styleable.LabelsView_selectedTextColor, defaultSelectTextColor)
            unSelectedTextColor =
                getColor(R.styleable.LabelsView_unSelectedTextColor, defaultSelectedTextColor)
            mWordMargin = getDimensionPixelOffset(R.styleable.LabelsView_WordMargin, 5.dp2px)
            mLineMargin = getDimensionPixelOffset(R.styleable.LabelsView_LineMargin, 5.dp2px)
            recycle()
        }
    }

    /**
     * 主要获取子view的尺寸计算ViewGroup的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        var maxWidth = MeasureSpec.getSize(widthMeasureSpec) - (paddingLeft + paddingRight)

        var contentHeight = 0;//总高度
        var lineWidth = 0;//行宽
        var maxLineWidth = 0;//最长的行宽
        var maxHeight = 0;//最长的行高
        var lineCount = 0;

        for (i in 0 until count) {
            val view = getChildAt(i)
            //子view自我测量
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            //预判加起来的宽度是否比maxWidth大,true则换行
            if ((lineWidth + view.measuredWidth + mWordMargin) > maxWidth) {
                ++lineCount
                contentHeight += mLineMargin + maxHeight
                maxLineWidth = maxLineWidth.coerceAtLeast(lineWidth)
                maxHeight = 0
                lineWidth = 0
            }
            //不需要换行则叠加数据
            lineWidth += (mWordMargin + view.measuredWidth)
            maxHeight = maxHeight.coerceAtLeast(view.measuredHeight)
        }

        contentHeight += maxHeight
        maxLineWidth = maxLineWidth.coerceAtLeast(lineWidth)

        val width = measureSize(widthMeasureSpec, maxLineWidth)
        val height = measureSize(heightMeasureSpec, contentHeight + paddingTop + paddingBottom)
        setMeasuredDimension(width, height)
    }

    /**
     * 将子view布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val maxWidth = r - l

        var x = paddingLeft
        var y = paddingTop
        var r = 0
        var b = 0;

        for (i in 0 until count) {
            val view = getChildAt(i)

            if ((x + view.measuredWidth + mWordMargin) > maxWidth) {
                //叠加达到换行效果
                y += view.measuredHeight + mLineMargin
                x = paddingLeft
            }
            r = x + view.measuredWidth
            b = y + view.measuredHeight
            view.layout(x, y, r, b)
            x += view.measuredWidth + mWordMargin
        }

    }

    private fun measureSize(measureSpec: Int, size: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        var result = when (specMode) {
            MeasureSpec.EXACTLY -> specSize //固定值 比如100dp或者match_parent
            MeasureSpec.AT_MOST -> specSize.coerceAtMost(size) //获取最小的数值 warp_content
            MeasureSpec.UNSPECIFIED -> specSize
            else -> specSize
        }

        result = result.coerceAtLeast(suggestedMinimumWidth)
        return resolveSizeAndState(result, measureSpec, 0)
    }

    /**
     * 添加控件需要的数据
     */
    fun setLabels(list: MutableList<String>) {
        list.forEach { it ->
            addLabel(it)
        }
    }

    /**
     * 添加控件
     */
    private fun addLabel(data: String) {
        val view = TextView(mContext)
        view.text = data
        view.setTextColor(Color.BLACK)
        view.setBackgroundColor(Color.WHITE)
        view.setPadding(8.dp2px, 5.dp2px, 8.dp2px, 5.dp2px)
        view.setOnClickListener(this)
        addView(view)
    }

    override fun onClick(v: View) {

    }
}