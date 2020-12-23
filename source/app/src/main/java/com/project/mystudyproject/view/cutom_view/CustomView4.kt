package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hopechart.baselib.utils.Logs
import kotlin.math.min

/**
 * 这个View演示了Paint的style属性
 */
class CustomView4 : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defaultAttr: Int) : super(
        context,
        attrs,
        defaultAttr
    )

    //构建画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 50f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的宽度和高度,两个圆之间最少间隔50个像素
        val usableWidth =
            (measuredWidth - paddingLeft - paddingRight ) / 4f

        val usableHeight = measuredHeight - paddingTop - paddingBottom - 2 * mPaint.strokeWidth

        //计算圆的可用半径
        val widthRadius = usableWidth / 2f
        val heightRadius = usableHeight / 2f
        val radius = min(widthRadius, heightRadius)
        //设置画笔样式为仅填充
        mPaint.color = Color.argb(255, 200, 200, 100)
        mPaint.style = Paint.Style.FILL
        canvas?.drawCircle(paddingLeft + usableWidth / 2f, measuredHeight / 2f, radius, mPaint)

        //设置画笔样式为仅描边
        mPaint.color = Color.argb(255, 200, 200, 100)
        mPaint.style = Paint.Style.STROKE
        canvas?.drawCircle(
            paddingLeft + usableWidth + usableWidth / 2f,
            measuredHeight / 2f,
            radius,
            mPaint
        )

        //设置画笔样式为描边和填充
        mPaint.color = Color.argb(255, 200, 200, 100)
        mPaint.style = Paint.Style.FILL_AND_STROKE
        canvas?.drawCircle(
            paddingLeft + 2 * usableWidth + usableWidth / 2f,
            measuredHeight / 2f,
            radius,
            mPaint
        )


        //两个圆的叠加效果
        //里面的圆设置仅填充
        mPaint.color = Color.argb(255, 200, 200, 100)
        mPaint.style = Paint.Style.FILL
        //绘制一个圆
        canvas?.drawCircle(paddingLeft + 3 * usableWidth + usableWidth / 2f,measuredHeight / 2f,radius,mPaint)
        //外面的圆设置仅描边，并且设置颜色半透明
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.argb(130,255,0,0)
        canvas?.drawCircle(paddingLeft + 3 * usableWidth + usableWidth / 2f,measuredHeight / 2f,radius,mPaint)
    }

}