package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 这个View演示了透明度
 */
class CustomView3 : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defAttr: Int) : super(
        context,
        attrs,
        defAttr
    )

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //可用的宽度和高度
        //每两个圆之间间隔50个像素
        //大圆是小圆的2倍
        val usableWidth =
            (measuredWidth - paddingLeft - paddingRight - 2 * mPaint.strokeWidth - 100) / 2.5f
        val usableHeight = measuredHeight - paddingTop - paddingBottom - 2 * mPaint.strokeWidth
        //计算需要绘制的大圆的半径
        val maxRadius = min(usableWidth / 2f, usableHeight / 2f)
        //设置需要绘制的小圆的半径
        val minRadius = maxRadius / 2f

        //设置小圆的颜色
        val minCircleColor = Color.argb(255, 255, 100, 150)
        //设置画笔颜色
        mPaint.color = minCircleColor
        //绘制小圆
        canvas?.drawCircle(
            paddingLeft + mPaint.strokeWidth + minRadius,
            measuredHeight / 2f,
            minRadius,
            mPaint
        )
        //设置大圆的颜色
        val maxCircleColor = Color.argb(130, 100, 200, 150)
        //设置画笔颜色
        mPaint.color = maxCircleColor
        //绘制大圆
        canvas?.drawCircle(
            paddingLeft + 3 * mPaint.strokeWidth + 2 * minRadius + 50 + maxRadius,
            measuredHeight / 2f,
            maxRadius,
            mPaint
        )

        //两个圆重合效果
        mPaint.color = minCircleColor
        canvas?.drawCircle(
            paddingLeft + 5 * mPaint.strokeWidth + 2 * minRadius + 2 * 50 + 3 * maxRadius,
            measuredHeight / 2f,
            minRadius,
            mPaint
        )
        mPaint.color = maxCircleColor
        canvas?.drawCircle(
            paddingLeft + 5 * mPaint.strokeWidth + 2 * minRadius + 2 * 50 + 3 * maxRadius,
            measuredHeight / 2f,
            maxRadius,
            mPaint
        )
    }
}