package com.hopechart.baselib.widget

import android.content.Context
import android.graphics.Typeface
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.hopechart.baselib.R
import com.hopechart.baselib.utils.Logs

/**
 *@time 2020/5/21
 *@user 张一凡
 *@description
 *@introduction
 */
class TwoTextView : LinearLayout {

    //第一种字体颜色
    private var mFirstColor = 0

    //第一种字体大小
    private var mFirstTextSize:Float  = 0f

    //第一种文字字体，这里主要是加粗/取消加粗显示
    private var mFirstTextStyle = Typeface.NORMAL
    //第一个TextView的文字
    private var mFirstText: String = ""


    //第二种字体颜色
    private var mSecondColor = 0
    //第二种字体大小
    private var mSecondTextSize: Float = 0f
    //第二种文字字体，这里主要是加粗/取消加粗显示
    private var mSecondTextStyle = Typeface.NORMAL
    //第二个TextView的文字
    private var mSecondText: String = ""

    //两个TextView之间的距离
    private var marginTextView: Int = 0

    //第一个TextView
    private lateinit var mFirstTextView: TextView
    //第二个TextView
    private lateinit var mSecondTextView: TextView


    constructor (context: Context?) :
            this(context, null)

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) :
            this(context, attrs, -1)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {

        val array = context?.obtainStyledAttributes(attrs,R.styleable.TwoTextView)
        array?.let {
            this.mFirstColor =
                it.getColor(
                    R.styleable.TwoTextView_first_text_color,
                    resources.getColor(R.color.black)
                )
            this.mFirstTextSize = array.getDimensionPixelSize(
                R.styleable.TwoTextView_first_text_size,
                resources.getDimension(R.dimen.sp_14).toInt()
            ).toFloat()

            this.mFirstTextStyle =
                array.getInt(R.styleable.TwoTextView_first_text_style, Typeface.NORMAL)
            this.mFirstText = array.getString(R.styleable.TwoTextView_first_text).toString()


            this.mSecondColor =
                it.getColor(
                    R.styleable.TwoTextView_second_text_color,
                    resources.getColor(R.color.black)
                )
            this.mSecondTextSize = array.getDimensionPixelSize(
                R.styleable.TwoTextView_second_text_size,
                14
            ).toFloat()

            this.mSecondTextStyle =
                array.getInt(R.styleable.TwoTextView_second_text_style, Typeface.NORMAL)
            this.mSecondText = array.getString(R.styleable.TwoTextView_second_text).toString()
            this.marginTextView = array.getDimensionPixelSize(R.styleable.TwoTextView_margin_text_view,0)

        }
        array?.recycle()
        initView()
    }

    //初始化两个TextView
    private fun initView() {
        //横向
        this.orientation = HORIZONTAL
        initFirstTextView()
        initSecondTextView()
    }

    //初始化第一个TextView
    private fun initFirstTextView(){
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        mFirstTextView = TextView(context)
        mFirstTextView.text = mFirstText
        mFirstTextView.setTextColor(mFirstColor)
        mFirstTextView.gravity = Gravity.CENTER
        mFirstTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mFirstTextSize)
        mFirstTextView.typeface = Typeface.defaultFromStyle(mFirstTextStyle)
        mFirstTextView.layoutParams = layoutParams
        mFirstTextView.maxLines = 1
        mFirstTextView.isSingleLine = true
        mFirstTextView.filters = arrayOf<InputFilter>(LengthFilter(10))
        this.addView(mFirstTextView)
    }

    //初始化第二个TextView
    private fun initSecondTextView(){
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        mSecondTextView = TextView(context)
        mSecondTextView.text = mSecondText
        mSecondTextView.gravity = Gravity.CENTER
        mSecondTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mSecondTextSize)
        mSecondTextView.typeface = Typeface.defaultFromStyle(mSecondTextStyle)
        layoutParams.leftMargin = marginTextView
        mSecondTextView.layoutParams = layoutParams
        mSecondTextView.maxLines = 1
        mSecondTextView.isSingleLine = true
        mSecondTextView.setTextColor(mSecondColor)
        this.addView(mSecondTextView)
    }

    //设置第一个TextView的文本
    fun setFirstText(text: String){
        mFirstTextView.text = text
    }

    fun setFirstText(resource: Int){
        mFirstTextView.text = context.resources.getText(resource)
    }

    //设置第二个TextView的文本
    fun setSecondText(text: String){
        mSecondTextView.text = text
    }
}