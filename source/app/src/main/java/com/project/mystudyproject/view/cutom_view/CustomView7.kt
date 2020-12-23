package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 这个View中学习如何绘制点和多个点的相关知识
 */
class CustomView7 : View {

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

    private var mStartDistance = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mStartDistance = 0f
        //设置颜色
        mPaint.color = Color.argb(255, 255, 0, 0)
        //设置画笔宽度
        mPaint.strokeWidth = 10f
        //设置起点
        mStartDistance += 100f
        //绘制一个点
        canvas?.drawPoint(mStartDistance, measuredHeight / 2f, mPaint)

        mPaint.color = Color.argb(0xff,0,0xff,0)
        //绘制多个点
        //设置起点
        mStartDistance += 50
        //设置点的集合
        val points1 = floatArrayOf(
            mStartDistance, measuredHeight / 5f,
            mStartDistance, measuredHeight / 5f * 2f,
            mStartDistance
        )
        canvas?.drawPoints(points1, mPaint)

        //设置颜色
        mPaint.color = Color.argb(0xff,0,0,0xff)
        //绘制多个点
        mStartDistance += 50
        //设置点的集合
        val points2 = floatArrayOf(
            mStartDistance, measuredHeight / 5f,
            mStartDistance, measuredHeight / 5f * 2f,
            mStartDistance
        )
        canvas?.drawPoints(points2, 1, 2, mPaint)
    }
}