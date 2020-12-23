package com.project.mystudyproject.view.test_rect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @创建者 张一凡
 * @创建时间 2020/12/23 14:27
 * @描述
 * @修改者和修改信息
 */
class TestRectView3: TestRectView2 {


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var mIntersect = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mIntersect){
            //执行次方法后 mRectF 会变为 mRect和mPointRectF两个矩形相交的矩形区域部分
            mRectF.intersect(mPointRectF)
            mIntersect = false
            mPointX = -1f
            mPointY = -1f
            invalidate()
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mIntersect = false
        when(event?.action){
            MotionEvent.ACTION_UP -> {
                mPointX = event.x
                mPointY = event.y
                mIntersect = true
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }
}