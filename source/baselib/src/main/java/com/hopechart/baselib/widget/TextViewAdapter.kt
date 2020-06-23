package com.hopechart.baselib.widget

import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 *@time 2020/4/30
 *@user 张一凡
 *@description TextView相关的DataBindingAdapter
 *@introduction
 */
class TextViewAdapter {

    companion object {
        //设置四个方向的drawable
        @JvmStatic
        @BindingAdapter("setDrawableLeft")
        fun setDrawableLeft(view: TextView, drawableRes: Int) {
            val drawable = view.context.resources.getDrawable(drawableRes, null)
            view.setCompoundDrawablesWithIntrinsicBounds(
                drawable,
                view.compoundDrawablesRelative[1],
                view.compoundDrawablesRelative[2],
                view.compoundDrawablesRelative[3]
            )
        }

        @JvmStatic
        @BindingAdapter("setDrawableTop")
        fun setDrawableTop(view: TextView, drawableRes: Int) {
            val drawable = view.context.resources.getDrawable(drawableRes, null)
            view.setCompoundDrawablesWithIntrinsicBounds(
                view.compoundDrawablesRelative[0],
                drawable,
                view.compoundDrawablesRelative[2],
                view.compoundDrawablesRelative[3]
            )
        }

        @JvmStatic
        @BindingAdapter("setDrawableRight")
        fun setDrawableRight(view: TextView, drawableRes: Int) {
            val drawable = view.context.resources.getDrawable(drawableRes, null)
            view.setCompoundDrawablesWithIntrinsicBounds(
                view.compoundDrawablesRelative[0],
                view.compoundDrawablesRelative[1],
                drawable,
                view.compoundDrawablesRelative[3]
            )
        }

        @JvmStatic
        @BindingAdapter("setDrawableBottom")
        fun setDrawableBottom(view: TextView, drawableRes: Int) {
            val drawable = view.context.resources.getDrawable(drawableRes, null)
            view.setCompoundDrawablesWithIntrinsicBounds(
                view.compoundDrawablesRelative[0],
                view.compoundDrawablesRelative[1],
                view.compoundDrawablesRelative[2],
                drawable
            )
        }


        //对文字添加下划线
        @JvmStatic
        @BindingAdapter("setUnderLine")
        fun setTextUnderLine(view: TextView,setUnderLine: Boolean = true) {
            if(setUnderLine){
                view.paint.flags = Paint. UNDERLINE_TEXT_FLAG
            }
        }
    }

}