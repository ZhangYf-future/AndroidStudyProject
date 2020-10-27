package com.project.mystudyproject.view

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MaskFilterView : View {

    private var mMaskColor: Int = Color.parseColor("#1BA8F7")
        set(value) {
            field = value
            invalidate()
        }

    //绘制发光效果的画笔
    private val mMaskPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = mMaskColor
            this.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    private  val mNormalPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = mMaskColor
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawRect(100f,100f,110f,300f,mMaskPaint)
        //canvas?.drawRect(103f,103f,107f,300f,mNormalPaint)
    }
}