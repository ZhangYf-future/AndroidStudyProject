package com.project.mystudyproject.view.cutom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.hopechart.baselib.utils.Logs

/**
 * 这个View演示了修改Canvas的背景
 */
class CustomView5 : View {

    var num = 0;

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defAttr: Int) : super(
        context,
        attrs,
        defAttr
    ){
        this.setOnClickListener {
            if(num >= 2)
                num = 0
            else
                num ++
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when(num){
            0 -> {
                canvas?.drawColor(Color.argb(255,0,255,0))
            }

            1 -> {
                canvas?.drawARGB(255,255,0,0)
            }

            2 -> {
                canvas?.drawRGB(0,0,255)
            }
        }
    }
}