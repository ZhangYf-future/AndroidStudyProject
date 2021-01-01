package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestPathView4
 * @Author: zyf
 * @Date: 2020/12/29 13:44
 * @Description: 这个类演示给Path添加圆角矩形路径
 * @update: 更新者和更新内容
 */
class TestPathView4: View {

    constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int): super(context, attrs, defStyleAttr)

    constructor(context: Context,attrs: AttributeSet?): this(context,attrs,0)

    constructor(context: Context): this(context,null)

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff,0,0xff,0xff)
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
    }

    //路径
    private val mPath by lazy {
        Path()
    }

    //左边圆角矩形所在的矩形
    private val mLeftRectF by lazy {
        RectF()
    }

    //右边圆角矩形所在的矩形范围
    private val mRightRectF by lazy {
        RectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0xff,0xee,0xee,0xee))

        val usableHeight = measuredHeight - paddingTop - paddingBottom
        val usableWidth = measuredWidth - paddingLeft - paddingRight

        //绘制左边的圆角矩形
        mLeftRectF.set(
            paddingLeft + usableWidth / 8f + mPaint.strokeWidth / 2f,
            paddingTop + usableHeight / 4f + mPaint.strokeWidth / 2f,
            measuredWidth / 2f - usableWidth / 8f - mPaint.strokeWidth / 2f,
            measuredHeight - usableHeight / 4f - mPaint.strokeWidth / 2f
        )

        //四个圆角的数组
        val radiusArray = floatArrayOf(
            10f,10f,20f,20f,30f,30f,40f,40f
        )

        //将左边的圆角矩形添加到路径中
        mPath.addRoundRect(mLeftRectF,radiusArray,Path.Direction.CW)


        //设置右边的圆角矩形的范围
        mRightRectF.set(
            measuredWidth / 2f + usableWidth / 8f + mPaint.strokeWidth / 2f,
            paddingTop + usableHeight / 4f + mPaint.strokeWidth / 2f,
            measuredWidth - usableWidth / 8f - mPaint.strokeWidth / 2f,
            measuredHeight - usableHeight / 4f - mPaint.strokeWidth / 2f
        )

        //将右边的圆角矩形添加到路径中
        mPath.addRoundRect(mRightRectF,10f,10f,Path.Direction.CCW)

        //绘制路径
        canvas?.drawPath(mPath,mPaint)
    }

}