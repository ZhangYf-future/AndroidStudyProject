package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @ClassName: TestTextView7
 * @Author: zyf
 * @Date: 2021/1/23 14:14
 * @Description: 这个View演示了逐个指定文字的位置并绘制
 * @update: 更新者和更新内容
 */
class TestTextView7 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //需要绘制的文本
    private val mString = "这是一段测试文本"
    private val mCharArray = charArrayOf('这', '也', '是', '一', '段', '测', '试', '文', '本')

    //绘制文本的画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xee, 0, 0)
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }

    //第一段文本中每一个字符所在的坐标点
    private val mFirstTextEveryPos by lazy {
        FloatArray(mString.length * 2)
    }

    //第二段文本中每一个字符所在的坐标点
    private val mSecondTextEveryPos by lazy {
        FloatArray(mCharArray.size * 2)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //可用的宽度
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom
        //第一段文本中每一个字所可以用的高度
        val firstATextHeight = usableHeight / mString.length
        //设置第一段文本每一个字符上边的坐标
        for (i in mFirstTextEveryPos.indices) {
            if (i % 2 == 0) {
                //x的坐标始终不变
                mFirstTextEveryPos[i] = paddingLeft + usableWidth / 4f
            } else {
                //y轴的坐标变化
                mFirstTextEveryPos[i] = ((i - 1) / 2) * firstATextHeight + firstATextHeight / 2f
            }
        }

        //绘制第一段文本
        canvas?.drawPosText(mString, mFirstTextEveryPos, mPaint)

        //第二段文本中每一个字符可以使用的高度
        val secondATextHeight = usableHeight / mCharArray.size
        for (i in mSecondTextEveryPos.indices) {
            if (i % 2 == 0) {
                //x的坐标始终不变
                mSecondTextEveryPos[i] = paddingLeft + usableWidth * 0.75f
            } else {
                //y轴的坐标变化
                mSecondTextEveryPos[i] = ((i - 1) / 2) * secondATextHeight + secondATextHeight / 2f
            }
        }

        //绘制第二段文本
        canvas?.drawPosText(mCharArray, 0, mCharArray.size, mSecondTextEveryPos, mPaint)
    }
}