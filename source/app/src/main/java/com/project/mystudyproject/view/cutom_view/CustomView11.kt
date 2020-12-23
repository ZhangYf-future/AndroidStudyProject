package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * 这个View演示了如何绘制一个椭圆
 */
class CustomView11 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //外边的矩形
    private val mOutRectF = RectF()

    //里面的矩形
    private val mInnerRectF = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //构建外部的矩形
        val outRectLeft = paddingLeft.toFloat()
        val outRectTop = paddingTop.toFloat()
        val outRectRight = usableWidth / 2f
        val outRectBottom = measuredHeight - paddingBottom.toFloat()
        mOutRectF.set(outRectLeft, outRectTop, outRectRight, outRectBottom)
        //绘制外面的矩形
        canvas?.drawRect(mOutRectF, mPaint)

        mPaint.color = Color.argb(0xbb, 0xf0, 0xe0, 0)
        mPaint.strokeWidth = 20f
        mPaint.style = Paint.Style.STROKE
        //设置里面矩形的属性,为了考虑到画笔宽度的影响，所以应该在外部矩形的基础上，在创建一个较小的矩形，沿着这个较小的矩形绘制椭圆，最后的结果就是椭圆的边和外部矩形的边相切
        mInnerRectF.set(
            mOutRectF.left + mPaint.strokeWidth / 2f,
            mOutRectF.top + mPaint.strokeWidth / 2f,
            mOutRectF.right - mPaint.strokeWidth / 2f,
            mOutRectF.bottom - mPaint.strokeWidth / 2f
        )
        canvas?.drawOval(mInnerRectF, mPaint)

        //使用第二种方式绘制一个椭圆
        mOutRectF.set(
            paddingLeft + usableWidth / 2f,
            paddingTop.toFloat(),
            measuredWidth - paddingRight.toFloat(),
            measuredHeight - paddingBottom.toFloat()
        )

        mPaint.color = Color.DKGRAY
        mPaint.style = Paint.Style.FILL
        //绘制外部的矩形
        canvas?.drawRect(mOutRectF, mPaint)


        mPaint.color = Color.argb(0xbb, 0xbb, 0, 0)
        mPaint.strokeWidth = 50f
        mPaint.style = Paint.Style.FILL_AND_STROKE

        //使用第二种方式绘制椭圆
        canvas?.drawOval(
            mOutRectF.left + mPaint.strokeWidth / 2f,
            mOutRectF.top + mPaint.strokeWidth / 2f,
            mOutRectF.right - mPaint.strokeWidth / 2f,
            mOutRectF.bottom - mPaint.strokeWidth / 2f,
            mPaint
        )
    }
}