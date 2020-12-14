package com.project.mystudyproject.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hopechart.baselib.utils.Logs

class CircleView: View {

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
        }
    }

    //定义一个接口，当前的View移动时将位置传递出去
    var moveXListener: ((translationX: Float) -> Unit)? = null
    var moveYListener: ((translationY: Float) -> Unit)? = null

    private val mRect by lazy {
        RectF()
    }
    constructor(context: Context): this(context, null){

    }

    constructor(context: Context, attributes: AttributeSet?):this(context, attributes, 0){

    }

    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int): super(
        context,
        attributes,
        defStyleAttr
    ){

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(50, 50)
        setMeasuredDimension(
            100,
            100
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, measuredWidth / 2f, mPaint)
    }



    //用户手指按下的位置
    private var mTouchX: Float = 0f
    private var mTouchY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mTouchX = event.x
                mTouchY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                this.translationX += event.x - mTouchX
                this.translationY += event.y - mTouchY
                return true
            }

            MotionEvent.ACTION_UP -> {


            }
        }

        return super.onTouchEvent(event)
    }

    override fun setTranslationX(translationX: Float) {
        super.setTranslationX(translationX)
        moveXListener?.invoke(translationX)
    }

    override fun setTranslationY(translationY: Float) {
        super.setTranslationY(translationY)
        moveYListener?.invoke(translationY)
    }
}