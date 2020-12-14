package com.project.mystudyproject.animation.property.transition

import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLaunchWithAnimation2Binding

/**
 * 使用动画启动Activity第二个页面
 */
class LaunchActivityWithAnimation2Activity : BaseActivity<ActivityLaunchWithAnimation2Binding>() {

    override fun getLayoutId(): Int = R.layout.activity_launch_with_animation2

    override fun doClick(view: View) {
        super.doClick(view)
        when(view.id){
            R.id.tv1 ->{
                onBackPressed()
            }
        }
    }
}