package com.project.mystudyproject.animation.property

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.text.Layout
import android.transition.Transition
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
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
            R.id.btn_load_animator_with_xml ->
                loadAnimatorWithXML()
            R.id.btn_load_animation_with_xml ->
                loadAnimationWithXML()
            R.id.btn_load_my_interpolator ->
                useMyInterpolator()
            R.id.btn_load_animation_list_with_xml ->
                loadAnimationListWithXML()
            R.id.btn_use_my_type_evaluator ->
                useMyTypeEvaluator()
            R.id.btn_use_property_values_holder ->
                usePropertyValuesHolder()
            R.id.btn_use_key_frame ->
                useKeyFrame()
            R.id.btn_use_animator_set_more_animator ->
                useAnimatorSetMoreAnimator()
            R.id.btn_use_property_values_holder_more_animator ->
                usePropertyValuesHolderMoreAnimator()
            R.id.btn_use_view_property_animator_more_animator ->
                useViewPropertyAnimatorMoreAnimator()
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

    //从XML文件中添加一个属性动画资源并设置到View上
    private fun loadAnimatorWithXML() {
        AnimatorInflater.loadAnimator(this, R.animator.animator_view_translation_alpha).apply {
            setTarget(mBinding.btnLoadAnimatorWithXml)
            start()
        }
    }

    //从XML文件中添加一个视图动画资源并设置到View上
    private fun loadAnimationWithXML() {
        val animAll = AnimationUtils.loadAnimation(this, R.anim.anim_all)
        mBinding.btnLoadAnimationWithXml.startAnimation(animAll)
    }

    //使用自定义的插值器
    private fun useMyInterpolator() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.view_scale)
        mBinding.btnLoadMyInterpolator.startAnimation(anim)
    }

    //加载逐帧动画并启动
    private fun loadAnimationListWithXML() {
        val backResource = mBinding.btnLoadAnimationListWithXml.background
        if (backResource is Animatable) {
            backResource.start()
        }
    }

    //加载并设置视图状态变更的动画资源
    private fun loadStateListAnimator() {
        val stateListAnimator =
            AnimatorInflater.loadStateListAnimator(this, R.drawable.animator_scale)
        mBinding.btnUseStateListAnimator.stateListAnimator = stateListAnimator
    }

    //使用自定义的TypeEvaluator来设置View的Margin
    private fun useMyTypeEvaluator() {
        val marginParams =
            mBinding.btnUseMyTypeEvaluator.layoutParams as LinearLayout.LayoutParams

        val endParams = LinearLayout.LayoutParams(marginParams)
        endParams.leftMargin += 200
        endParams.rightMargin += 200

        val animator = ValueAnimator.ofObject(
            TypeEvaluator<ViewGroup.MarginLayoutParams> { fraction, startValue, endValue ->
                val params = LinearLayout.LayoutParams(startValue)
                val left =
                    startValue.leftMargin + (endValue.leftMargin - startValue.leftMargin) * fraction
                val right =
                    startValue.rightMargin + (endValue.rightMargin - startValue.rightMargin) * fraction
                val top =
                    startValue.topMargin + (endValue.topMargin - startValue.topMargin) * fraction
                val bottom =
                    startValue.bottomMargin + (endValue.bottomMargin - startValue.bottomMargin) * fraction
                params.leftMargin = left.toInt()
                params.topMargin = top.toInt()
                params.rightMargin = right.toInt()
                params.bottomMargin = bottom.toInt()
                params
            },
            marginParams, endParams
        )
        animator.addUpdateListener {
            val params = it.animatedValue as ViewGroup.LayoutParams
            mBinding.btnUseMyTypeEvaluator.layoutParams = params
        }

        animator.interpolator = OvershootInterpolator()
        animator.duration = 1000
        animator.start()


    }

    //使用PropertyValuesHolder设置动画
    private fun usePropertyValuesHolder() {
        val holder1 = PropertyValuesHolder.ofFloat("translationX", 0f, 10f, 100f)
        val holder2 = PropertyValuesHolder.ofFloat("translationY", 0f, 10f, 100f)

        ObjectAnimator.ofPropertyValuesHolder(mBinding.btnUseMyTypeEvaluator, holder1, holder2)
            .apply {
                interpolator = OvershootInterpolator()
                duration = 5 * 1000
                start()
            }
    }

    //使用关键帧来构建动画
    private fun useKeyFrame() {
        //定义关键帧
        val keyFrame1 = Keyframe.ofFloat(0f, 0f)
        val keyFrame2 = Keyframe.ofFloat(0.5f, 30f)
        val keyFrame3 = Keyframe.ofFloat(1.0f, 360f)
        //获取PropertyValuesHolder对象
        val holder = PropertyValuesHolder.ofKeyframe("rotation", keyFrame1, keyFrame2, keyFrame3)
        //构建动画并开始执行
        ObjectAnimator.ofPropertyValuesHolder(mBinding.btnUseKeyFrame, holder).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 5 * 1000
            start()
        }
    }

    //使用AnimatorSet实现组合动画
    private fun useAnimatorSetMoreAnimator() {
        val target = mBinding.btnUseAnimatorSetMoreAnimator
        val translationAnimator =
            ObjectAnimator.ofFloat(target, "translationX", target.measuredWidth.toFloat() / 2)
        val scaleXAnimator = ObjectAnimator.ofFloat(target, "scaleX", 2f)
        val scaleYAnimator = ObjectAnimator.ofFloat(target, "scaleY", 2f)
        val set = AnimatorSet()
        set.play(translationAnimator).with(scaleXAnimator).with(scaleYAnimator)
        set.interpolator = OvershootInterpolator()
        set.duration = 5000
        set.start()
    }

    //使用PropertyValuesHolder实现组合动画
    private fun usePropertyValuesHolderMoreAnimator() {
        val target = mBinding.btnUsePropertyValuesHolderMoreAnimator
        val translationHolder =
            PropertyValuesHolder.ofFloat("translationX", target.measuredWidth.toFloat() / 2)
        val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 2.0f)
        val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 2.0f)
        ObjectAnimator.ofPropertyValuesHolder(target, translationHolder, scaleXHolder, scaleYHolder)
            .apply {
                interpolator = OvershootInterpolator()
                duration = 5 * 1000
                start()
            }
    }

    //使用ViewPropertyAnimator实现组合动画
    private fun useViewPropertyAnimatorMoreAnimator() {
        val target = mBinding.btnUseViewPropertyAnimatorMoreAnimator
        target.animate()
            .translationX(target.measuredWidth.toFloat() / 2)
            .scaleX(2f)
            .scaleY(2f)
            .start()
    }

}