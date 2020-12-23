package com.project.mystudyproject.view.test_rect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hopechart.baselib.utils.Logs
import java.lang.IllegalArgumentException

/**
 * @创建者 张一凡
 * @创建时间 2020/12/23 10:49
 * @描述 这个View用于学习判断一个矩形是否包含另一个矩形的方法，这里会先根据手指的位置绘制一个矩形，并跟随手指移动，
 * 当移动到指定的矩形区域内部时会改变颜色
 * @修改者和修改信息 张一凡 添加判断两个矩形是否相交的方法
 */
open class TestRectView2 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //手指按下的位置
    protected open var mPointX: Float = 0f
    protected open var mPointY: Float = 0f

    //手指所在的位置的矩形的宽度
    private val mPointRectWidth = 80f

    //手指所在的位置的矩形
    protected  open val mPointRectF by lazy {
        RectF()
    }

    //指定的矩形
    protected open val mRectF by lazy {
        RectF()
    }

    //画笔
    protected open val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    //根据用户的手指的位置生成一个矩形
    private fun createRecrWithPoint() {

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        if (usableWidth < mPointRectWidth || usableHeight < mPointRectWidth)
            throw IllegalArgumentException("可用宽度和高度不足以绘制相关矩形")

        //左边的限制
        val limitLeft = paddingLeft.toFloat()
        //上边的限制
        val limitTop = paddingTop.toFloat()
        //右边的限制
        val limitRight = measuredWidth - paddingRight.toFloat()
        //下边的限制
        val limitBottom = measuredHeight - paddingBottom.toFloat()


        var right: Float
        var bottom: Float


        var left = if (mPointX < limitLeft) limitLeft else mPointX
        var top = if (mPointY < limitTop) limitTop else mPointY

        if (left + mPointRectWidth > limitRight) {
            right = limitRight
            left = limitRight - mPointRectWidth
        } else {
            right = left + mPointRectWidth
        }

        if (top + mPointRectWidth > limitBottom) {
            bottom = limitBottom
            top = limitBottom - mPointRectWidth
        } else {
            bottom = top + mPointRectWidth
        }
        mPointRectF.set(left, top, right, bottom)
    }

    //设置固定矩形的属性
    private fun createFixedRect() {
        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        val left = paddingLeft + measuredWidth / 2f - usableWidth / 4f
        val top = paddingTop + measuredHeight / 2f - usableHeight / 4f
        val right = measuredWidth / 2f + usableWidth / 4f
        val bottom = measuredHeight / 2f + usableHeight / 4f

        mRectF.set(left, top, right, bottom)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        createFixedRect()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.argb(0xff, 0xee, 0xee, 0xee))

        //绘制固定的矩形
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.argb(0xff, 0xcc, 0, 0)
        canvas?.drawRect(mRectF, mPaint)

        //根据用户的点击位置生成移动的矩形
        createRecrWithPoint()

        //判断两个矩形是否相交
        //静态方法判断
//        val isIntersects = RectF.intersects(mRectF, mPointRectF)
//        Logs.e("两个矩形是否相交:$isIntersects")
//        //使用成员方法判断两个矩形是否相交
//        val isIntersects1 =
//            mPointRectF.intersects(mRectF.left, mRectF.top, mRectF.right, mRectF.bottom)
//        Logs.e("使用成员方法判断两个矩形是否相交:$isIntersects1")

        //绘制可移动的矩形
        mPaint.color = if (mRectF.contains(mPointRectF))
            Color.argb(0xaa, 0, 0, 0)
        else Color.argb(
            0xaa,
            0,
            0xff,
            0
        )
        canvas?.drawRect(mPointRectF, mPaint)

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
        }
        return super.onTouchEvent(event)
    }
}