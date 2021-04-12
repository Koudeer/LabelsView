package com.koudeer.labels

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding

class LabelsView<T> : ViewGroup, View.OnClickListener {
    private val TAG = "LabelsView"

    private var mWordMargin: Int = 0;//文本控件间隔
    private var mLineMargin: Int = 0;//行距
    private var selectedBackgroundColor: Int = 0;//选中背景颜色
    private var unSelectedBackgroundColor: Int = 0;//未选中背景颜色
    private var selectedTextColor: Int = 0;//文本选中颜色
    private var unSelectedTextColor: Int = 0;//文本未选中颜色
    private var textPaddingLeft = 0;
    private var textPaddingRight = 0;
    private var textPaddingTop = 0;
    private var textPaddingBottom = 0;
    private var textPadding = 0;
    private var labelTextSize = 0f;

    private val defaultSelectedBgColor = Color.parseColor("#2196F3") //默认选中背景颜色
    private val defaultUnSelectedBgColor = Color.WHITE //默认未选中背景颜色
    private val defaultSelectTextColor = Color.BLACK //未选中文本颜色
    private val defaultSelectedTextColor = Color.BLACK //选中文本颜色

    public val dataList = mutableListOf<T>()

    private var mContext: Context

    constructor(context: Context) : super(context) {
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(context, attrs)
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
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

            textPaddingLeft =
                getDimensionPixelOffset(R.styleable.LabelsView_textPaddingLeft, 10.dp2px);
            textPaddingRight =
                getDimensionPixelOffset(R.styleable.LabelsView_textPaddingRight, 10.dp2px);
            textPaddingTop =
                getDimensionPixelOffset(R.styleable.LabelsView_textPaddingTop, 5.dp2px);
            textPaddingBottom =
                getDimensionPixelOffset(R.styleable.LabelsView_textPaddingBottom, 5.dp2px);

            textPadding = getDimensionPixelOffset(R.styleable.LabelsView_textPadding, 0)

            labelTextSize = getDimension(R.styleable.LabelsView_labelTextSize, 12.sp2px)

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
            //因为lineWidth是mWordMargin + view.measuredWidth 所以预判的时候不需要加上mWordMargin
            if ((lineWidth + view.measuredWidth) > maxWidth) {
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
    fun setLabels(list: MutableList<LabelData<T>>) {
        list.forEach { it ->
            addLabel(it)
        }
    }

    /**
     * 添加控件
     */
    private fun addLabel(data: LabelData<T>) {
        val view = LabelTextView<T>(mContext)
        view.text = data.title
        view.data = data.data
        view.setTextColor(unSelectedTextColor)
        view.setBackgroundColor(unSelectedBackgroundColor)
        view.setOnClickListener(this)
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize)
        if (textPadding != 0) {
            view.setPadding(textPadding)
        } else {
            view.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom)
        }

        addView(view)
    }

    override fun onClick(v: View) {
        if (v !is LabelTextView<*>) return

        if (v.isClick) {
            //选中状态变为未选中状态
            v.setBackgroundColor(unSelectedBackgroundColor)
            v.setTextColor(unSelectedTextColor)
            dataList.remove(v.data as T)
        } else {
            //未选中状态变为选中状态
            v.setBackgroundColor(selectedBackgroundColor)
            v.setTextColor(selectedTextColor)
            dataList.add(v.data as T)
        }
        v.isClick = !v.isClick
        listener?.onChange(dataList)
    }

    private var listener: onLabelChangeListener<T>? = null

    public interface onLabelChangeListener<T> {
        fun onChange(list: List<T>)
    }

    public fun setLabelChangeListener(listener: onLabelChangeListener<T>): Unit {
        this.listener = listener
    }
}