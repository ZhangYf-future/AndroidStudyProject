package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * 这个View演示了弧的绘制
 */
class CustomView12 : View {

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

    //第一个弧所在的矩形
    private val mFirstOutRectF = RectF()
    private val mFirstInnerRectF = RectF()

    //第二个弧所在的矩形
    private val mSecondOutRectF = RectF()
    private val mSecondInnerRectF = RectF()

    //绘制第二个弧的画笔
    private val mSecondPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //绘制第三个弧的画笔
    private val mThirdPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //第三个弧所在的矩形
    private val mThirdOutRectF = RectF()
    private val mThirdInnerRectF = RectF()

    //第四个弧的画笔
    private val mFourthPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //第四个弧所在的矩形
    private val mFourthOutRectF = RectF()
    private val mFourthInnerRectF = RectF()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //设置画笔属性
        mPaint.color = Color.argb(0xff, 0xee, 0xee, 0xee)
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL

        mFirstOutRectF.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            usableWidth / 2f,
            usableHeight / 2f
        )
        //绘制第一个矩形
        canvas?.drawRect(mFirstOutRectF, mPaint)

        mPaint.color = Color.argb(0xff, 0xbb, 0, 0)
        mPaint.strokeWidth = 20f
        mPaint.style = Paint.Style.STROKE

        //设置第一个椭圆所在的矩形
        mFirstInnerRectF.set(
            mFirstOutRectF.left + mPaint.strokeWidth / 2f,
            mFirstOutRectF.top + mPaint.strokeWidth / 2f,
            mFirstOutRectF.right - mPaint.strokeWidth / 2f,
            mFirstOutRectF.bottom - mPaint.strokeWidth / 2f
        )

        //绘制第一个弧
        canvas?.drawArc(mFirstInnerRectF, 0f, 90f, true, mPaint)


        mSecondPaint.color = Color.argb(0xff, 0xdd, 0xdd, 0xdd)
        mSecondPaint.style = Paint.Style.FILL

        //设置第二个椭圆所在的矩形区域
        mSecondOutRectF.set(
            mFirstOutRectF.right,
            paddingTop.toFloat(),
            measuredWidth - paddingRight.toFloat(),
            usableHeight / 2f
        )

        canvas?.drawRect(mSecondOutRectF, mSecondPaint)

        mSecondPaint.color = Color.argb(0xff, 0xaa, 0xaa, 0)
        mSecondPaint.strokeWidth = 20f
        mSecondPaint.style = Paint.Style.STROKE

        mSecondInnerRectF.set(
            mSecondOutRectF.left + mPaint.strokeWidth / 2f,
            mSecondOutRectF.top + mPaint.strokeWidth / 2f,
            mSecondOutRectF.right - mPaint.strokeWidth / 2f,
            mSecondOutRectF.bottom - mPaint.strokeWidth / 2f
        )

        //绘制弧形
        canvas?.drawArc(mSecondInnerRectF, 0f, 90f, false, mSecondPaint)


        //绘制第三个弧
        mThirdPaint.color = Color.argb(0xff, 0xcc, 0xcc, 0xcc)
        mThirdPaint.style = Paint.Style.FILL

        mThirdOutRectF.set(
            paddingLeft.toFloat(),
            mFirstOutRectF.bottom,
            mFirstOutRectF.right,
            measuredHeight - paddingBottom.toFloat()
        )
        canvas?.drawRect(mThirdOutRectF, mThirdPaint)

        mThirdPaint.color = Color.argb(0xff, 0xff, 0xbb, 0)
        mThirdPaint.strokeWidth = 20f
        mThirdPaint.style = Paint.Style.FILL_AND_STROKE
        mThirdInnerRectF.set(
            mThirdOutRectF.left + mThirdPaint.strokeWidth / 2f,
            mThirdOutRectF.top + mThirdPaint.strokeWidth / 2f,
            mThirdOutRectF.right - mThirdPaint.strokeWidth / 2f,
            mThirdOutRectF.bottom - mThirdPaint.strokeWidth / 2f
        )
        canvas?.drawArc(mThirdInnerRectF, 0f, 90f, true, mThirdPaint)


        //绘制第四个弧
        mFourthPaint.color = Color.argb(0xff, 0xbb, 0xbb, 0xbb)
        mFourthPaint.style = Paint.Style.FILL

        mFourthOutRectF.set(
            mThirdOutRectF.right,
            mSecondOutRectF.bottom,
            mSecondOutRectF.right,
            mThirdOutRectF.bottom
        )

        canvas?.drawRect(mFourthOutRectF, mFourthPaint)


        mFourthPaint.color = Color.argb(0xff, 0xff, 0, 0xff)
        mFourthPaint.strokeWidth = 5f
        mFourthPaint.style = Paint.Style.FILL_AND_STROKE
        mFourthInnerRectF.set(
            mFourthOutRectF.left + mFourthPaint.strokeWidth / 2f,
            mFourthOutRectF.top + mFourthPaint.strokeWidth / 2f,
            mFourthOutRectF.right - mFourthPaint.strokeWidth / 2f,
            mFourthOutRectF.bottom - mFourthPaint.strokeWidth / 2f
        )
        canvas?.drawArc(mFourthInnerRectF, 0f, 90f, false, mFourthPaint)
    }
}