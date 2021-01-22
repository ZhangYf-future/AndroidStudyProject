package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView5
 * @Author: zyf
 * @Date: 2021/1/22 16:55
 * @Description: 这个View演示了在水平方向上拉伸文本
 * @update: 更新者和更新内容
 */
class TestTextView5 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    //绘制的文本
    private val mString = "这是测试文本(Test)"

    //普通文本的画笔
    private val mNormalPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xff, 0x33, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    //拉伸文本的画笔
    private val mScalePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xff, 0x33, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
            textScaleX = 2f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //绘制普通样式的文本
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop + usableHeight / 4f, mNormalPaint)

        //绘制水平拉伸的文本
        canvas?.drawText(
            mString,
            measuredWidth / 2f,
            paddingTop + usableHeight * 0.75f,
            mScalePaint
        )
    }
}