package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 这个View用来演示绘制线段
 */
class CustomView6 : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyle: Int) : super(
        context,
        attr,
        defStyle
    )

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            var startDistance = paddingLeft.toFloat()

            //绘制一条线
            mPaint.color = Color.argb(0xff, 0x00, 0x80, 0xff)
            mPaint.strokeWidth = 20f
            mPaint.style = Paint.Style.FILL
            it.drawLine(
                startDistance,
                paddingTop.toFloat(),
                startDistance,
                measuredHeight - paddingBottom.toFloat(),
                mPaint
            )

            startDistance += mPaint.strokeWidth + 60
            mPaint.color = Color.argb(0xff, 0xff, 0x80, 0x00)
            mPaint.strokeWidth = 40f
            mPaint.style = Paint.Style.FILL
            it.drawLine(
                startDistance,
                paddingTop.toFloat(),
                startDistance,
                measuredHeight - paddingBottom.toFloat(),
                mPaint
            )

            startDistance += mPaint.strokeWidth + 60
            //设置画笔为仅描边
            mPaint.style = Paint.Style.STROKE
            it.drawLine(
                startDistance,
                paddingTop.toFloat(),
                startDistance,
                measuredHeight - paddingBottom.toFloat(),
                mPaint
            )

            startDistance += mPaint.strokeWidth + 60

            //设置画笔为描边和填充
            mPaint.style = Paint.Style.FILL_AND_STROKE
            it.drawLine(
                startDistance,
                paddingTop.toFloat(),
                startDistance,
                measuredHeight - paddingBottom.toFloat(),
                mPaint
            )

            //绘制多条线段
            startDistance += mPaint.strokeWidth + 60

            mPaint.strokeWidth = 10f
            val pos = floatArrayOf(
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 80, measuredHeight - paddingBottom.toFloat(),
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 80, measuredHeight - paddingBottom.toFloat()
            )
            it.drawLines(pos, mPaint)

            startDistance += 100
            val pos1 = floatArrayOf(
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 80, measuredHeight - paddingBottom.toFloat(),
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 80
            )
            it.drawLines(pos1, mPaint)

            startDistance += 100

            val pos2 = floatArrayOf(
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 80, measuredHeight - paddingBottom.toFloat(),
                startDistance
            )
            it.drawLines(pos2, mPaint)


            startDistance += 100

            val pos3 = floatArrayOf(
                startDistance,paddingTop.toFloat(),
                startDistance, measuredHeight - paddingBottom.toFloat(),
                startDistance + 40, paddingTop.toFloat(),
                startDistance + 80, measuredHeight - paddingBottom.toFloat(),
                startDistance
            )
            it.drawLines(pos3, 2, 4, mPaint)
        }
    }
}