package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hopechart.baselib.utils.Logs

/**
 * @ClassName: TestTextView1
 * @Author: zyf
 * @Date: 2021/1/22 14:18
 * @Description: 这个页面演示了Paint样式对绘制的文本的影响
 * @update: 更新者和更新内容
 */
class TestTextView1 : View {

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
            color = Color.argb(0xff, 0x80, 0x80, 0)
            strokeWidth = 2f
            textSize = 40f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //设置画笔样式为填充
        mPaint.style = Paint.Style.FILL
        canvas?.drawText(mString, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)

        //设置画笔样式为描边
        mPaint.style = Paint.Style.STROKE
        canvas?.drawText(mString, paddingLeft.toFloat(), usableHeight / 3f, mPaint)

        //设置画笔为描边和填充
        mPaint.style = Paint.Style.FILL_AND_STROKE
        canvas?.drawText(mString, paddingLeft.toFloat(), usableHeight / 3f * 2f, mPaint)
    }
}