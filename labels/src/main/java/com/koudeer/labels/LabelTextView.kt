package com.koudeer.labels

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class LabelTextView : AppCompatTextView {

    public var isClick = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}