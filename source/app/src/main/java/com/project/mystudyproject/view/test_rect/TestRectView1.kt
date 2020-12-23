package com.project.mystudyproject.view.test_rect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @创建者 张一凡
 * @创建时间 2020/12/23 10:07
 * @描述 这个页面用于学习测试RectF包含某个点的方法，根据这个方法绘制的一个自定义View，当手指移动到这个矩形区域内的时候，这个
 * 矩形会被绘制出来，否则不会绘制出来
 * @修改者和修改信息
 */
class TestRectView1 : View {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //需要判断的矩形
    private val mRectF by lazy {
        RectF()
    }

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.FILL
        }
    }

    //用户点击的位置的坐标
    private var mPointX: Float = -1f
    private var mPointY: Float = -1f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        val left = paddingLeft + usableWidth / 4f
        val top = paddingTop + usableHeight / 4f
        val right = left + usableWidth / 2f
        val bottom = top + usableHeight / 2f
        mRectF.set(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.argb(0xff, 0, 0xcc, 0x88))

        //判断点击的位置是否在矩形内部
        if (mRectF.contains(mPointX, mPointY)) {
            canvas?.drawRect(mRectF, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPointX = event.x
                mPointY = event.y
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                mPointX = event.x
                mPointY = event.y
                invalidate()

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mPointX = -1f
                mPointY = -1f
                invalidate()

            }
        }
        return super.onTouchEvent(event)
    }

}