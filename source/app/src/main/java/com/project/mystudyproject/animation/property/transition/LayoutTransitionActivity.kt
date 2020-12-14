package com.project.mystudyproject.animation.property.transition

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLayoutTransitionBinding

/**
 * 过渡动画 -- 布局更改动画
 */
class LayoutTransitionActivity : BaseActivity<ActivityLayoutTransitionBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_layout_transition


    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_add_view ->
                testDefaultLayoutChangeAnimation()

            R.id.btn_set_custom_change_animation ->
                testCustomLayoutChangeAnimation()

            R.id.btn_use_scene ->
                startActivity(Intent(this, SceneTestActivity::class.java))

            R.id.btn_launch_activity_with_animation ->
                startActivity(Intent(this,LaunchActivityWithAnimation1Activity::class.java))
        }
    }

    //向LinearLayout中添加View用以测试默认的系统布局动画
    private fun testDefaultLayoutChangeAnimation() {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val textView = TextView(this)
        textView.layoutParams = params
        textView.setPadding(10)
        textView.pivotX = 0f
        textView.pivotY = 0f
        textView.setText(R.string.this_is_add_view)
        textView.setOnClickListener {
            mBinding.linearTestLayoutChangeAutoAnimation.removeView(it)
        }
        mBinding.linearTestLayoutChangeAutoAnimation.addView(textView)
    }

    //为布局更改添加自定义的动画
    //定义一个LayoutTransition
    private val layoutTransition by lazy {
        LayoutTransition().apply {
            this.setAnimator(LayoutTransition.APPEARING, mChildViewInAnimation)
            this.setAnimator(LayoutTransition.CHANGE_APPEARING, mChildViewInAnimation)
            this.setAnimator(LayoutTransition.DISAPPEARING, mChildViewExitAnimation)
            this.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, mChildViewExitAnimation)
        }
    }

    //定义子View进入的动画
    private val mChildViewInAnimation by lazy {
        ObjectAnimator.ofFloat(
            null,
            "x",
            mBinding.linearTestLayoutChangeAutoAnimation.right.toFloat(),
            0f
        )
    }

    //定义子View退出的动画
    private val mChildViewExitAnimation by lazy {
        ObjectAnimator.ofFloat(
            null,
            "x",
            0f,
            -mBinding.linearTestLayoutChangeAutoAnimation.right.toFloat()
        )
    }

    private fun testCustomLayoutChangeAnimation() {
        mBinding.linearTestLayoutChangeAutoAnimation.layoutTransition = layoutTransition
    }

}