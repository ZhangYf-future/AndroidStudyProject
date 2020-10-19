package com.project.mystudyproject

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.animation.AnimationHomeActivity
import com.project.mystudyproject.databinding.ActivityMainBinding
import com.project.mystudyproject.notification.home.NotificationHomeActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_about_notification -> {
                startActivity(Intent(this, NotificationHomeActivity::class.java))
            }
            R.id.btn_about_animation -> {
                startActivity(Intent(this, AnimationHomeActivity::class.java))
            }
        }
    }
}