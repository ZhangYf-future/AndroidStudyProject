package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 这个View用于学习绘制矩形的相关知识
 */
class CustomView8 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //绘制矩形的RectF
    private val mRectF = RectF()

    //绘制矩形的Rect
    private val mRect = Rect()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的宽度
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom
        //起始位置
        var startDistance = paddingLeft
        //绘制第一个矩形
        mPaint.color = Color.argb(0xff, 0xff, 0, 0)
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL
        canvas?.drawRect(
            startDistance.toFloat(),
            paddingTop.toFloat(),
            usableWidth / 3f,
            measuredHeight - paddingBottom.toFloat(),
            mPaint
        )

        //使用第二种方式绘制一个矩形
        mPaint.strokeWidth = 50f
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.argb(0xaa, 0, 0xff, 0)
        //这里要考虑到画笔的宽度
        val leftF = paddingLeft + usableWidth / 3f + mPaint.strokeWidth / 2f
        val topF = paddingTop.toFloat() + mPaint.strokeWidth / 2f
        val rightF = leftF + usableWidth / 3f - mPaint.strokeWidth
        val borromF = measuredHeight - paddingBottom.toFloat() - mPaint.strokeWidth / 2f
        mRectF.set(leftF, topF, rightF, borromF)
        canvas?.drawRect(mRectF, mPaint)

        //使用第三种方式绘制一个矩形
        mPaint.strokeWidth = 50f
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = Color.argb(0x88, 0, 0, 0xff)
        //同样要考虑到画笔的宽度
        val rectLeft = (rightF + mPaint.strokeWidth).toInt()
        val rectTop = (paddingTop + mPaint.strokeWidth / 2f).toInt()
        val rectRight = (rectLeft + usableWidth / 3f - mPaint.strokeWidth).toInt()
        val rectBottom =
            (measuredHeight - paddingBottom.toFloat() - mPaint.strokeWidth / 2f).toInt()
        mRect.set(rectLeft, rectTop, rectRight, rectBottom)
        canvas?.drawRect(mRect, mPaint)
    }
}