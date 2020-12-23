package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomView9 : View {

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

    //矩形
    private val mRectF = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //设置画笔属性
        mPaint.color = Color.argb(0xff, 0xff, 0x88, 0x50)
        mPaint.strokeWidth = 50f
        mPaint.style = Paint.Style.FILL_AND_STROKE

        //圆角矩形所在的矩形
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom
        //矩形的宽
        val rectWidth = usableWidth / 3f
        val rectHeight = usableHeight / 2f
        //设置矩形范围
        val rectLeft = measuredWidth / 2f - rectWidth / 2f
        val rectTop = measuredHeight / 2f - rectHeight / 2f
        val rectRight = measuredWidth / 2f + rectWidth / 2f
        val rectBottom = measuredHeight / 2f + rectHeight / 2f
        mRectF.set(rectLeft, rectTop, rectRight, rectBottom)
        //绘制圆角矩形
        canvas?.drawRoundRect(mRectF, 50f, 50f, mPaint)

        //上面的画笔的宽度
        val oldPaintWidth = mPaint.strokeWidth

        //使用第二种方法绘制一个圆角矩形:
        mPaint.color = Color.argb(0x99, 0xa0, 0xff, 0)
        mPaint.strokeWidth = 30f
        mPaint.style = Paint.Style.STROKE
        //绘制圆角矩形:
        canvas?.drawRoundRect(
            measuredWidth / 2f - rectWidth / 2f - oldPaintWidth / 2f - mPaint.strokeWidth / 2f,
            measuredHeight / 2f - rectHeight / 2f - oldPaintWidth / 2f - mPaint.strokeWidth / 2f,
            measuredWidth / 2f + rectWidth / 2f + oldPaintWidth / 2f + mPaint.strokeWidth / 2f,
            measuredHeight / 2f + rectHeight / 2f + oldPaintWidth / 2f + mPaint.strokeWidth / 2f,
            50f,
            50f,
            mPaint
        )
    }
}