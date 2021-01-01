package com.project.mystudyproject.view.test_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * @ClassName: TestPathView6
 * @Author: zyf
 * @Date: 2020/12/29 16:00
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
class TestPathView6 : View {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    //画笔
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(0xff, 0xff, 0, 0)
            style = Paint.Style.FILL
        }
    }

    //第一个矩形
    private val mFirstRectF by lazy {
        RectF()
    }

    //第一个路径
    private val mFirstPath by lazy {
        Path().apply {
            fillType = Path.FillType.WINDING
        }
    }

    //第二个路径
    private val mSecondPath by lazy {
        Path().apply {
            fillType = Path.FillType.EVEN_ODD
        }
    }

    //第二个矩形
    private val mSecondRectF by lazy {
        RectF()
    }

    //第三个路径
    private val mThirdPath by lazy {
        Path().apply {
            fillType = Path.FillType.INVERSE_WINDING
        }
    }

    //第三个矩形
    private val mThirdRectF by lazy {
        RectF()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制颜色
        canvas?.drawColor(Color.argb(0xff, 0xee, 0xee, 0xee))

        val usableWidth = measuredWidth - paddingLeft - paddingRight
        val usableHeight = measuredHeight - paddingTop - paddingBottom

        //将可用的区域分为四个部分，每一部分的可用宽度和高度
        val areaWidth = usableWidth / 2f
        val areaHeight = usableHeight / 2f

        //设置第一个矩形的位置
        mFirstRectF.set(
            paddingLeft + mPaint.strokeWidth / 2f + areaWidth / 6f,
            paddingTop + mPaint.strokeWidth / 2f + areaHeight / 6f,
            measuredWidth / 2f - mPaint.strokeWidth / 2f - areaWidth / 6f,
            measuredHeight / 2f - mPaint.strokeWidth / 2f - areaHeight / 6f
        )
        //添加矩形
        mFirstPath.addRect(mFirstRectF, Path.Direction.CCW)

        //左上角的圆
        val firstCircleRadius = min(areaWidth, areaHeight) / 3f - mPaint.strokeWidth / 2f
        //添加圆形
        mFirstPath.addCircle(
            paddingLeft + areaWidth / 3f * 2,
            paddingTop + areaHeight / 3f * 2,
            firstCircleRadius,
            Path.Direction.CCW
        )

        //绘制第一个路径
        canvas?.drawPath(mFirstPath, mPaint)

        //设置第二个矩形
        mSecondRectF.set(
            measuredWidth / 2f + mPaint.strokeWidth / 2f + areaWidth / 6f,
            paddingTop + mPaint.strokeWidth / 2f + areaHeight / 6f,
            measuredWidth - paddingRight - mPaint.strokeWidth / 2f - areaWidth / 6f,
            measuredHeight / 2f - mPaint.strokeWidth / 2f - areaHeight / 6f
        )

        //向第二个路径中添加矩形和圆形
        mSecondPath.fillType = Path.FillType.EVEN_ODD
        mSecondPath.addRect(mSecondRectF, Path.Direction.CW)
        mSecondPath.addCircle(
            measuredWidth - paddingRight - areaWidth / 3f,
            paddingTop + areaHeight / 3f * 2,
            firstCircleRadius,
            Path.Direction.CW
        )
        //绘制第二个路径
        canvas?.drawPath(mSecondPath, mPaint)
        
        //设置第三个矩形
        mThirdRectF.set(
            paddingLeft + mPaint.strokeWidth / 2f + areaWidth / 6f,
            measuredHeight / 2f + mPaint.strokeWidth / 2f + areaHeight / 6f,
            measuredWidth / 2f - mPaint.strokeWidth / 2f - areaWidth / 6f,
            measuredHeight - paddingBottom - areaHeight / 6f
        )

        //向第三个路径中添加矩形和圆形
        mThirdPath.fillType = Path.FillType.INVERSE_WINDING
        mThirdPath.addRect(mThirdRectF,Path.Direction.CW)
        mThirdPath.addCircle(paddingLeft + areaWidth / 3f * 2,measuredHeight - paddingBottom - areaHeight / 3f,firstCircleRadius,Path.Direction.CW)
        //绘制第三个路径
        canvas?.drawPath(mThirdPath,mPaint)
    }
}