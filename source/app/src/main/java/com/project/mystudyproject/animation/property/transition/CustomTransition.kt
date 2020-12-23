package com.project.mystudyproject.animation.property.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.hopechart.baselib.utils.Logs

class CustomTransition : Transition() {

    //保存x轴所在的位置
    private val mPointX = "custom_transition:view:x"
    private val mAlpha = "custon_transition:view:alpha"

    override fun captureStartValues(transitionValues: TransitionValues) {
        transitionValues.values[mPointX] = transitionValues.view.x

    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        transitionValues.values[mPointX] = transitionValues.view.x

    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        //Logs.e("createAnimator: \n startValues: $startValues \n endValues: $endValues")
        Logs.e("createAnimator:${startValues == null},${endValues == null}")

        if (startValues == null || endValues == null)
            return null

        //Fade

        if (startValues != null) {
            Logs.e("设置动画:${startValues.view}")
            val startAnimator = ObjectAnimator.ofFloat(
                startValues.view,
                "x",
                startValues.values[mPointX] as Float,
                -(sceneRoot.measuredWidth.toFloat())
            )
//            startAnimator.addUpdateListener {
//                Logs.e("开始动画:${it.animatedValue},$it")
//            }
            return startAnimator
        } else if (endValues != null) {
            return ObjectAnimator.ofFloat(
                endValues.view,
                "x",
                sceneRoot.measuredWidth.toFloat(),
                endValues.values[mPointX] as Float
            )
        }
        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}