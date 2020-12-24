package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 这个View用于演示向路径中添加矩形
 * 同时演示了根据不同的路径方向绘制文字
 */
class TestPathView3 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val mPath by lazy {
        Path()
    }

    private val mRightPath by lazy {
        Path()
    }

    private val mLeftRectF by lazy {
        RectF()
    }

    private val mRightRectF by lazy {
        RectF()
    }

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
    }

    //绘制文字的画笔
    private val mTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GREEN
            textSize = 30f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //左边的矩形
        val leftRectLeft = paddingLeft + mPaint.strokeWidth / 2f + usableWidth / 8f
        val leftRectTop = paddingTop + mPaint.strokeWidth / 2f + usableHeight / 4f
        val leftRectRight = measuredWidth / 2f - mPaint.strokeWidth / 2f - usableWidth / 8f
        val leftRectBottom =
            measuredHeight - paddingBottom - mPaint.strokeWidth / 2f - usableHeight / 4f
        mLeftRectF.set(
            leftRectLeft,
            leftRectTop,
            leftRectRight,
            leftRectBottom
        )

        //右边的矩形
        val rightRectLeft = measuredWidth / 2f + usableWidth / 8f + mPaint.strokeWidth / 2f
        val rightRectTop = paddingTop + usableHeight / 4f + mPaint.strokeWidth / 2f
        val rightRectRight =
            measuredWidth - paddingRight - usableWidth / 8f - mPaint.strokeWidth / 2f
        val rightRectBottom =
            measuredHeight - paddingBottom - usableHeight / 4f - mPaint.strokeWidth / 2f

        mRightRectF.set(rightRectLeft, rightRectTop, rightRectRight, rightRectBottom)

        //按照顺时针的方向向路径中添加左边的矩形
        mPath.addRect(mLeftRectF, Path.Direction.CW)
        //按照逆时针的方向向路径中添加右边的矩形
        //mPath.addRect(mRightRectF, Path.Direction.CCW)
        //按照逆时针的方式添加矩形
        mRightPath.addRect(mRightRectF,Path.Direction.CCW)

        //绘制路径
        canvas?.drawPath(mPath, mPaint)

        canvas?.drawPath(mRightPath,mPaint)

        //按照路径绘制文字
        val testStr = "测试按路径绘制文字"
        canvas?.drawTextOnPath(testStr,mPath,0f,0f,mTextPaint)
        canvas?.drawTextOnPath(testStr,mRightPath,0f,0f,mTextPaint)
    }
}