package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView6
 * @Author: zyf
 * @Date: 2021/1/23 13:34
 * @Description: 这个View演示了canvas.drawText()相关重载函数的用法
 * @update: 更新者和更新内容
 */
class TestTextView6 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //需要绘制的文本
    private val mString = "由String定义的文本"
    private val mCharSequence: CharSequence = "由CharSequence定义的文本"
    private val mCharArray = charArrayOf('由', 'c', 'h', 'a', 'r', '数', '组', '定', '义', '的', '文', '本')

    //绘制文本的画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xee, 0, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0Xff, 0xdd, 0xdd, 0xdd))
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom
        val aHeight = usableHeight / 3f
        //绘制第一个文本
        canvas?.drawText(mString, 1, 9, measuredWidth / 2f, paddingTop + aHeight / 2f, mPaint)

        //绘制第二个文本
        canvas?.drawText(
            mCharSequence,
            1,
            mCharSequence.length,
            measuredWidth / 2f,
            paddingTop + aHeight + aHeight / 2f,
            mPaint
        )

        //绘制第三个文本
        canvas?.drawText(
            mCharArray,
            1,
            5,
            measuredWidth / 2f,
            paddingTop + 2 * aHeight + aHeight / 2f,
            mPaint
        )
    }
}