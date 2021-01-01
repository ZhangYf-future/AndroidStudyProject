package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * @ClassName: TestPathView5
 * @Author: zyf
 * @Date: 2020/12/29 14:29
 * @Description: 这个自定义View演示了向Path中添加圆形，椭圆和圆弧
 * @update: 更新者和更新内容
 */
class TestPathView5 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xbb, 0xff, 0)
            strokeWidth = 20f
            style = Paint.Style.STROKE
        }
    }

    //路径
    private val mPath by lazy {
        Path()
    }

    //椭圆所在的矩形
    private val mOvalRectF by lazy {
        RectF()
    }

    //圆弧所在的椭圆所在的矩形
    private val mArcRectF by lazy {
        RectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //圆形的半径
        val radius = min(usableWidth / 2f, usableHeight.toFloat()) / 2f - mPaint.strokeWidth / 2f
        //向路径中添加一个园
        mPath.addCircle(paddingLeft + usableWidth / 4f, measuredHeight / 2f, radius, Path.Direction.CCW)

        //设置椭圆所在的矩形
        mOvalRectF.set(
            paddingLeft + usableWidth / 2f + mPaint.strokeWidth / 2f,
            paddingTop + mPaint.strokeWidth / 2f,
            measuredWidth - paddingRight  - mPaint.strokeWidth / 2f,
            measuredHeight - paddingBottom - mPaint.strokeWidth / 2f
        )

        //向路径中添加椭圆
        mPath.addOval(mOvalRectF,Path.Direction.CW)

        //设置圆弧所在的矩形
        mArcRectF.set(
            paddingLeft + usableWidth / 4f + mPaint.strokeWidth / 2f,
            paddingTop + mPaint.strokeWidth / 2f,
            measuredWidth - paddingRight - usableWidth / 4f - mPaint.strokeWidth / 2f,
            measuredHeight - paddingBottom - mPaint.strokeWidth / 2f
        )

        //向路径中添加圆弧
        mPath.addArc(mArcRectF,-150f,150f)

        //绘制路径
        canvas?.drawPath(mPath, mPaint)
    }
}