package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 这个View演示了在路径上设置/添加弧形
 * 路径的连续性演示
 * 以及如何突破路径的连续性
 */
class TestPathView2 : View {
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

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLUE
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
    }

    //弧线所在的举行
    private val mRectF by lazy {
        RectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.argb(0xff, 0xee, 0xee, 0xee))

        mPath.moveTo(paddingLeft.toFloat(), paddingTop.toFloat())

        //向当前Path设置一条弧线
        val right = measuredWidth / 2f
        val bottom = measuredHeight - paddingBottom - mPaint.strokeWidth / 2f
        mRectF.set(
            paddingLeft + mPaint.strokeWidth / 2f,
            paddingTop + mPaint.strokeWidth / 2f,
            right,
            bottom
        )
        //这里是以路径的起点开始绘制的，也就是moveTo之后的位置
        mPath.arcTo(mRectF, -40f, 110f)
        //这里强制以弧线的起点作为绘制的起点
        //mPath.arcTo(mRectF, -40f, 110f,true)
        //通过addArc方法添加弧线，也可以忽略路径的连续性
        mPath.addArc(mRectF,150f,90f)
        //绘制路径
        canvas?.drawPath(mPath, mPaint)
    }


}