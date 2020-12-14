package com.project.mystudyproject.animation.property.transition

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.transition.ChangeImageTransform
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLaunchWithAnimation1Binding

/**
 * 使用动画启动Activity第一个页面
 */
class LaunchActivityWithAnimation1Activity : BaseActivity<ActivityLaunchWithAnimation1Binding>() {

    override fun getLayoutId(): Int = R.layout.activity_launch_with_animation1

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.tv1 -> {
                toSecondActivity()
            }
        }
    }

    //启动到第二个Activity
    private fun toSecondActivity() {
        //5.0及以上版本才能使用过渡动画跳转Activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(
                Intent(this, LaunchActivityWithAnimation2Activity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        } else {
            //普通情况下跳转Activity
            startActivity(Intent(this, LaunchActivityWithAnimation2Activity::class.java))
        }
    }
}