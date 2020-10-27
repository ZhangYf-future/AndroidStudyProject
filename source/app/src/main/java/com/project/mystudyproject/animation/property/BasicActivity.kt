package com.project.mystudyproject.animation.property

import android.animation.*
import android.annotation.SuppressLint
import android.text.Layout
import android.transition.Transition
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.animation.addListener
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityBasicBinding

@SuppressLint("ObjectAnimatorBinding")
class BasicActivity : BaseActivity<ActivityBasicBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_basic

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_value_animator ->
                createValueAnimator()
            R.id.btn_value_animator_of_object ->
                createValueAnimatorOfObject()
            R.id.btn_use_value_animator_to_Margin ->
                createValueAnimatorToMargin()
            R.id.btn_use_object_animator_to_move ->
                createObjectAnimatorToMove()
            R.id.btn_use_animator_set ->
                createAnimatorSet()
            R.id.btn_add_animator_to_view_group ->
                addAnimatorToViewGroup()
        }
    }

    //使用ValueAnimator添加动画效果
    private fun createValueAnimator() {
        ValueAnimator.ofInt(0, 100).apply {
            duration = 10 * 1000
            this.addUpdateListener {
                Logs.e("动画执行中:${it.animatedValue}")
            }
            start()
        }
    }

    //使用ValueAnimator.ofObject来创建一个动画
    private fun createValueAnimatorOfObject() {
        ValueAnimator.ofObject(IntEvaluator(), 0, 100).run {
            duration = 3 * 1000
            addUpdateListener {
                Logs.e("动画执行中:${it.animatedValue}")
            }
            start()
        }
    }

    //将ValueAnimator关联到View的Margin属性
    private fun createValueAnimatorToMargin() {
        val params =
            mBinding.btnUseValueAnimatorToMargin.layoutParams as ViewGroup.MarginLayoutParams
        ValueAnimator.ofInt(params.leftMargin, params.leftMargin + 100).run {
            duration = 3 * 1000
            addUpdateListener {
                params.leftMargin = (it.animatedValue as Int)
                params.rightMargin = (it.animatedValue as Int)
                mBinding.btnUseValueAnimatorToMargin.layoutParams = params
            }
            start()
        }
    }

    //使用ObjectAnimator移动View
    private fun createObjectAnimatorToMove() {
        ObjectAnimator.ofFloat(
            mBinding.btnUseObjectAnimatorToMove,
            "translationX",
            0f, 100f
        ).run {
            duration = 5 * 1000
            start()
        }
    }

    //使用AnimatorSet编排多个动画
    private fun createAnimatorSet() {
        //首先创建第一个动画，View的alpha将会从1变为0
        val targetView = mBinding.btnUseAnimatorSet
        val alphaAnimator = ObjectAnimator.ofFloat(targetView, "alpha", 1f, 0.2f).apply {
            duration = 1 * 1000
        }
        //第二个动画，将View向右移出窗口
        val params = targetView.layoutParams as ViewGroup.MarginLayoutParams
        val moveX = 100
        val translationAnimator =
            ObjectAnimator.ofFloat(targetView, "translationX", 0f, moveX.toFloat()).apply {
                duration = 3 * 1000
            }
        //创建AnimationSet并设置两个动画同时执行
        AnimatorSet().apply {
            //设置每个动画执行时间为5秒
            //duration = 5 * 1000
            //两个动画同时执行
            //this.play(alphaAnimator).with(translationAnimator)
            //先播放alphaAnimator再播放translationAnimator
            //this.play(translationAnimator).after(alphaAnimator)
            this.play(alphaAnimator).before(translationAnimator)
            this.addListener(onStart = {
                Logs.e("动画开始")
            }, onEnd = {
                Logs.e("动画结束")
            })

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    Logs.e("开始播放动画")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    Logs.e("动画结束")
                }

            })
            start()
        }
    }

    private val mViewGroupTransition by lazy {
        LayoutTransition()
    }

    //添加View或者View由不显示到显示的时候View进入的动画
    private val mViewInAnimator by lazy {
        val width = mBinding.testViewGrouoAnimator.measuredWidth
        ObjectAnimator.ofFloat(null, "translationX", width.toFloat(), 0f)
    }

    //删除View或者View由显示到不显示的时候执行的动画
    private val mViewOutAnimator by lazy {
        val width = mBinding.testViewGrouoAnimator.measuredWidth
        ObjectAnimator.ofFloat(null, "translationX", 0f, width.toFloat())
    }

    //为ViewGroup的布局更改添加动画
    private fun addAnimatorToViewGroup() {
        mViewGroupTransition.setAnimator(LayoutTransition.APPEARING, mViewInAnimator)
        mViewGroupTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, mViewInAnimator)
        mViewGroupTransition.setAnimator(LayoutTransition.DISAPPEARING, mViewOutAnimator)
        mViewGroupTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, mViewOutAnimator)
        mBinding.testViewGrouoAnimator.layoutTransition = mViewGroupTransition
        //待添加的View
        val button = Button(this)
        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        button.text = "待添加的View"
        button.setOnClickListener {
            mViewOutAnimator.target = it
            mBinding.testViewGrouoAnimator.removeView(it)
//            mViewOutAnimator.target = it
//            it.visibility = View.GONE
        }
        mViewInAnimator.target = button
        mBinding.testViewGrouoAnimator.addView(button)
    }
}