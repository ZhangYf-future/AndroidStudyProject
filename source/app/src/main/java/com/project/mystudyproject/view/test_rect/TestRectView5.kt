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
 * @创建时间 2020/12/23 16:12
 * @描述
 * @修改者和修改信息
 */
class TestRectView5 : View {

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

        canvas?.drawColor(Color.argb(0xff, 0xee, 0xee, 0xee))

        //第一个矩形为空矩形
        val unionX = paddingLeft + measuredWidth / 3f
        val unionY = paddingTop + measuredHeight / 3f

        mFirstRectF.union(unionX, unionY)

        //绘制第一个合并后的矩形
        mPaint.color = Color.argb(0xff, 0xcc, 0, 0)
        canvas?.drawRect(mFirstRectF, mPaint)

        //设置第二个矩形的位置
        val left = paddingLeft + measuredWidth / 2f + mPaint.strokeWidth / 2f
        val top = paddingTop + measuredHeight / 2f + mPaint.strokeWidth / 2f
        val right = measuredWidth - paddingRight - mPaint.strokeWidth / 2f
        val bottom = measuredHeight - paddingBottom - mPaint.strokeWidth / 2f

        mSecondRectF.set(left, top, right, bottom)
        //绘制第二个矩形
        mPaint.color = Color.argb(0xcc, 0xff, 0xff, 0)
        canvas?.drawRect(mSecondRectF, mPaint)

        //设置第二个举行和目标点合并
        mSecondRectF.union(unionX, unionY)
        //绘制合并后的第二个矩形
        mPaint.color = Color.argb(0xaa, 0, 0xee, 0xbb)
        canvas?.drawRect(mSecondRectF, mPaint)
    }
}