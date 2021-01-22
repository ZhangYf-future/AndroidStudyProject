package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView2
 * @Author: zyf
 * @Date: 2021/1/22 14:51
 * @Description: 这个页面演示了Paint setTextAlign属性对绘制的文本的影响
 * @update: 更新者和更新内容
 */
class TestTextView2 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //需要绘制的文本
    private val mString = "这是一段测试文本(Test)"

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 40f
            color = Color.argb(0xff, 0, 0xff, 0xff)
            style = Paint.Style.FILL
            strokeWidth = 2f
        }
    }

    //绘制线的画笔
    private val mLinePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = 2f
            color = Color.argb(0xff, 0x99, 0, 0)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0xff, 0xdd, 0xdd, 0xdd))
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //绘制基准线，文本的位置就是根据这根线来确定的
        canvas?.drawLine(
            measuredWidth / 2f,
            paddingTop.toFloat(),
            measuredWidth / 2f,
            measuredHeight - paddingBottom.toFloat(),
            mLinePaint
        )

        //设置居左
        mPaint.textAlign = Paint.Align.LEFT
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop.toFloat(), mPaint)

        //设置居中
        mPaint.textAlign = Paint.Align.CENTER
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop + usableHeight / 3f, mPaint)

        //设置居右
        mPaint.textAlign = Paint.Align.RIGHT
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop + usableHeight / 3f * 2f, mPaint)

    }
}