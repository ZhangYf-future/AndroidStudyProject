package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView3
 * @Author: zyf
 * @Date: 2021/1/22 16:15
 * @Description: 这个页面演示了绘制文本的时候添加粗体，下划线和删除线
 * @update: 更新者和更新内容
 */
class TestTextView3 : View {

    //构造函数
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //绘制测试文本
    private val mString = "这是测试文本(Test)"

    //绘制普通文本的画笔
    private val mNormalPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xee, 0, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    //绘制包含各种样式的文本的画笔
    private val mStylePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xee, 0, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
            //设置粗体
            isFakeBoldText = true
            //设置下划线
            isUnderlineText = true
            //设置删除线
            isStrikeThruText = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //绘制普通的文本
        canvas?.drawText(mString, measuredWidth / 2f, paddingTop + usableHeight / 4f, mNormalPaint)
        //绘制带有样式的文本
        canvas?.drawText(
            mString,
            measuredWidth / 2f,
            paddingTop + usableHeight * 0.75f,
            mStylePaint
        )
    }

}