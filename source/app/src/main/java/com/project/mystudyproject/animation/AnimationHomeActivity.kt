package com.project.mystudyproject.animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.animation.property.PropertyAnimationHomeActivity
import com.project.mystudyproject.databinding.ActivityAnimationHomeBinding

/**
 * 动画首页
 */
class AnimationHomeActivity : BaseActivity<ActivityAnimationHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_animation_home

    override fun doClick(view: View) {
        super.doClick(view)
        when(view.id){
            R.id.btn_property_animation ->
                startActivity(Intent(this,PropertyAnimationHomeActivity::class.java))
        }
    }
}