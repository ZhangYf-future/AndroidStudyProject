package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView4
 * @Author: zyf
 * @Date: 2021/1/22 16:35
 * @Description: 这个View演示了绘制带有斜度的文本
 * @update: 更新者和更新内容
 */
class TestTextView4 : View {

    //构造函数
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //绘制文本
    private val mString = "这是测试文本(Test)"

    //普通文本的画笔
    private val mNormalPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0, 0x33, 0xff)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    //带有斜度的文本的画笔
    private val mSkewPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0, 0x33, 0xff)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0xff, 0xcc, 0xcc, 0xcc))
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom
        val aHeight = usableHeight / 4f
        //绘制正常的文本
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop + aHeight / 2f, mNormalPaint)

        //设置斜度为0
        mSkewPaint.textSkewX = 0f
        canvas?.drawText(
            mString,
            measuredWidth / 2f,
            paddingTop + aHeight + aHeight / 2f,
            mSkewPaint
        )
        //设置向右倾斜
        mSkewPaint.textSkewX = -0.5f
        canvas?.drawText(
            mString,
            measuredWidth / 2f,
            paddingTop + 2 * aHeight + aHeight / 2f,
            mSkewPaint
        )

        //设置向左倾斜
        mSkewPaint.textSkewX = 0.5f
        canvas?.drawText(
            mString,
            measuredWidth / 2f,
            paddingTop + 3 * aHeight + aHeight / 2f,
            mSkewPaint
        )
    }
}