package com.project.mystudyproject.view.test_rect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
/**
 * @创建者 张一凡
 * @创建时间 2020/12/23 14:56
 * @描述
 * @修改者和修改信息
 */
class TestRectView4 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //第一个矩形
    private val mFirstRectF by lazy {
        RectF()
    }

    //第二个矩形
    private val mSecondRectF by lazy {
        RectF()
    }

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置第一个矩形
        val firstLeft = paddingLeft + mPaint.strokeWidth / 2f
        val firstTop = paddingTop + mPaint.strokeWidth / 2f
        val firstRight = measuredWidth / 2f - mPaint.strokeWidth / 2f
        val firstBottom = measuredHeight / 2f - mPaint.strokeWidth / 2f
        mFirstRectF.set(firstLeft, firstTop, firstRight, firstBottom)

        //绘制第一个矩形
        mPaint.color = Color.argb(0xff, 0xff, 0, 0)
        canvas?.drawRect(mFirstRectF, mPaint)


        //设置第二个矩形
        val secondLeft = measuredWidth / 2f + mPaint.strokeWidth / 2f
        val secondtop = measuredHeight / 2f + mPaint.strokeWidth / 2f
        val secondRight = measuredWidth - paddingRight - mPaint.strokeWidth / 2f
        val secondBottom = measuredHeight - paddingBottom - mPaint.strokeWidth / 2f
        mSecondRectF.set(secondLeft, secondtop, secondRight, secondBottom)
        //绘制第二个矩形
        mPaint.color = Color.argb(0xff, 0, 0xff, 0)
        canvas?.drawRect(mSecondRectF, mPaint)

        //设置两个矩形合并
        mFirstRectF.union(mSecondRectF)
        //绘制合并后的矩形
        mPaint.color = Color.argb(0x99, 0, 0, 0xff)
        canvas?.drawRect(mFirstRectF, mPaint)
    }
}