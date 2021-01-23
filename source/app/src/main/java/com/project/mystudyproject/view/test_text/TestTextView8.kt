package com.project.mystudyproject.view.test_text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.math.min

/**
 * @ClassName: TestTextView8
 * @Author: zyf
 * @Date: 2021/1/23 14:59
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
class TestTextView8 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //需要绘制的文本
    private val mString = "这是需要沿路径绘制的一段文本"
    private val mString2 = "这也是需要沿路径绘制的一段文本，还能截取其中一段"
    private val mCharArray by lazy {
        mString2.toCharArray()
    }

    //第一个路径
    private val mFirstPath by lazy {
        Path()
    }

    //第二个路径
    private val mSecondPath by lazy {
        Path()
    }

    //绘制文本的画笔
    private val mDrawTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xee, 0, 0)
            textSize = 32f
        }
    }

    //绘制路径的画笔
    private val mDrawPathPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0, 0xee, 0x88)
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.argb(0xff, 0xdd, 0xdd, 0xdd))
        //可用的宽度
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        //可用的高度
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //圆的半径
        val radius = (min(usableWidth / 2f, usableHeight.toFloat())) / 2f - 40

        //向第一个路径中添加一个圆
        mFirstPath.addCircle(
            paddingLeft + usableWidth / 4f,
            measuredHeight / 2f,
            radius - 2 * mDrawPathPaint.strokeWidth,
            Path.Direction.CCW
        )
        //绘制第一个路径
        canvas?.drawPath(mFirstPath, mDrawPathPaint)
        //沿第一条路径绘制第一段文本
        canvas?.drawTextOnPath(mString, mFirstPath, 0f, 0f, mDrawTextPaint)

        //向第二个路径中添加一个圆
        mSecondPath.addCircle(
            paddingLeft + usableWidth * 0.75f,
            measuredHeight / 2f,
            radius - 2 * mDrawPathPaint.strokeWidth,
            Path.Direction.CCW
        )

        //绘制第二个路径
        canvas?.drawPath(mSecondPath, mDrawPathPaint)
        //沿第二个路径绘制第二段文本
        canvas?.drawTextOnPath(mCharArray, 1, 10, mSecondPath, 80f, 30f, mDrawTextPaint)

        canvas?.drawTextRun(mString, 2, 8, 0, mString.length, 100f, 100f, false, mDrawTextPaint)
    }
}