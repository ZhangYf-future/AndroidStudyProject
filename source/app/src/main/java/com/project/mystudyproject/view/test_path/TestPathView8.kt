package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * @ClassName: TestPathView8
 * @Author: zyf
 * @Date: 2021/1/7 16:29
 * @Description: 这个View演示了绘制一个蜘蛛网状图
 * @update: 更新者和更新内容
 */
class TestPathView8: View {

    constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int):super(context,attrs, defStyleAttr)

    constructor(context: Context,attrs: AttributeSet?): this(context,attrs,0)

    constructor(context: Context): this(context,null)

    //绘制网状图线条的画笔
    private val mLinePaint by lazy {
        Paint()
    }

    //线条的宽度
    private val mLineWidth = 10f

    //需要绘制的了六边形的数量
    private val mDrawHexagonNum = 5

    //绘制数值的画笔
    private val mValuePaint by lazy {
        Paint()
    }

    //可用的宽度和高度
    private val mUsableWidth by lazy {
        measuredWidth - paddingLeft - paddingRight
    }

    //可用的高度
    private val mUsableHeight by lazy {
        measuredHeight - paddingTop - paddingBottom
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //设置画笔属性
        mLinePaint.let {
            it.isAntiAlias = true
            it.color = Color.argb(0xff,0x99,0x9,0x99)
            it.style = Paint.Style.STROKE
            it.strokeWidth = mLineWidth
        }

        mValuePaint.let {
            it.isAntiAlias = true
            it.color = Color.argb(0xff,0xff,0x99,0)
            it.style = Paint.Style.FILL
        }


        //绘制多个六边形

    }


    private fun drawMultipleHexagon(canvas: Canvas){
        //可用的尺寸
        val usableSize = min(mUsableWidth,mUsableHeight) - 10

        //计算间隔
        val interval = (usableSize - (mDrawHexagonNum * mLineWidth)) / mDrawHexagonNum

        //绘制每一个六边形
        for(i in 0 until mDrawHexagonNum){

        }
    }
}