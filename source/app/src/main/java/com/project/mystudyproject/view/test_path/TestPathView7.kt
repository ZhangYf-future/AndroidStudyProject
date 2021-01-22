package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.hopechart.baselib.utils.Logs
import kotlin.math.min

/**
 * @ClassName: TestPathView7
 * @Author: zyf
 * @Date: 2021/1/7 15:37
 * @Description: 这个View演示了使用Path.reset()和Path.rewind()重置路径
 * @update: 更新者和更新内容
 */
class TestPathView7 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //画笔
    private val mPaint by lazy {
        Paint()
    }

    //左边的路径
    private val mLeftPath by lazy {
        Path()
    }

    //右边的路径
    private val mRightPath by lazy {
        Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //设置画笔属性
        mPaint.let {
            it.isAntiAlias = true
            it.color = Color.argb(0xff, 0xff, 0x44, 0)
        }

        //可用的宽度和高度
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //计算圆形的可用半径
        val radius = min(usableWidth / 2, usableHeight) / 2 - 10

        //设置左边路径的属性
        mLeftPath.fillType = Path.FillType.INVERSE_WINDING
        mLeftPath.reset()
        mLeftPath.addCircle(
            paddingLeft + usableWidth / 4f,
            measuredHeight / 2f,
            radius.toFloat(),
            Path.Direction.CW
        )
        //canvas?.drawPath(mLeftPath, mPaint)

        //设置右边路径的属性
        mRightPath.fillType = Path.FillType.INVERSE_WINDING
        mRightPath.rewind()
        mRightPath.addCircle(
            measuredWidth - paddingLeft - usableWidth / 4f,
            measuredHeight / 2f,
            radius.toFloat(),
            Path.Direction.CW
        )
        //绘制路径
        canvas?.drawPath(mRightPath, mPaint)
    }
}