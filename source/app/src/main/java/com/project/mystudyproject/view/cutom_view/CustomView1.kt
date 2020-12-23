package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView1 : View {

    //定义画笔
    private val mPaint by lazy {
        Paint().apply {
            color = Color.RED
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defAttr: Int) : super(
        context,
        attrs,
        defAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制一个圆形
        canvas?.drawCircle(100f, 100f, 50f, mPaint)
    }
}