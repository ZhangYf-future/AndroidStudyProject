package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 这个View演示了圆形的绘制方法
 */
class CustomView10 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0, 0xff, 0xff)
            strokeWidth = 50f
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val usableWidth = measuredWidth - paddingLeft - paddingRight - mPaint.strokeWidth
        val usableHeight = measuredHeight - paddingTop - paddingBottom - mPaint.strokeWidth

        //圆形的半径
        val radius = min(usableWidth, usableHeight) / 2f

        //绘制圆形
        canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, mPaint)
    }
}