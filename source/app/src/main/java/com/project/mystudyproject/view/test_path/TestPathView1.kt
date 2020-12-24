package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @创建者 张一凡
 * @创建时间 2020/12/23 17:04
 * @描述 这个View用于学习直线路径
 * @修改者和修改信息
 */
class TestPathView1 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
    }

    private val mPath by lazy {
        Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        mPath.moveTo(paddingLeft.toFloat(), paddingTop.toFloat())
        //设置一条直线
        mPath.lineTo(paddingLeft + usableWidth / 2f, paddingTop + usableHeight / 2f)

        canvas?.drawPath(mPath, mPaint)

        //设置一个三角形
        //移动到三角形的顶点
        mPath.moveTo(measuredWidth / 2f, paddingTop.toFloat())
        //连接到三角形最左边的点
        mPath.lineTo(
            measuredWidth / 2f - 50,
            measuredHeight - paddingBottom.toFloat() - mPaint.strokeWidth / 2f
        )
        //连接到三角形最右边的点
        mPath.lineTo(
            measuredWidth / 2f + 50,
            measuredHeight - paddingBottom.toFloat() - mPaint.strokeWidth / 2f
        )
        //关闭三角形
        mPath.close()
        //绘制三角形
        canvas?.drawPath(mPath, mPaint)
    }

}