package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CustomView2 : View {

    private val mPaint by lazy {
        Paint().apply {
            this.color = Color.RED
            this.style = Paint.Style.STROKE
            this.strokeWidth = 10.0f
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defAttr: Int) : super(
        context,
        attrs,
        defAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的宽度
        //两个圆之间间隔50个像素
        val usableWidth = measuredWidth - paddingLeft - paddingRight - 50 -  mPaint.strokeWidth * 2
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom - mPaint.strokeWidth * 2

        //计算圆的半径
        val radius = min(usableHeight / 2f, usableWidth / 4f)

        //绘制第一个圆,没有抗锯齿效果
        canvas?.drawCircle(paddingLeft.toFloat() + radius, measuredHeight / 2f, radius, mPaint)

        //设置抗锯齿效果
        mPaint.isAntiAlias = true
        canvas?.drawCircle(
            paddingLeft.toFloat() + 2 * radius + 50 + radius,
            measuredHeight / 2f,
            radius,
            mPaint
        )
    }
}