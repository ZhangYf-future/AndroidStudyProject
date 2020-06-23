package com.hopechart.baselib.utils.anim

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import com.hopechart.baselib.utils.Logs

/**
 *@time 2020/5/6
 *@user 张一凡
 *@description 使用动画移动
 *@introduction
 */
class AnimationMarginUtils {
    companion object{
        //设置margin属性
        fun animMarginTop(view: View,end: Int,duration: Int = 200){
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            val begin = params.topMargin
            if(begin == end)
                return
            val objectValue: ValueAnimator = ValueAnimator.ofInt(begin,end)
            objectValue.addUpdateListener {
                val value = it.animatedValue as Int
                params.topMargin = value
                view.layoutParams = params
            }
            objectValue.duration = duration.toLong()
            objectValue.start()
        }
    }

}