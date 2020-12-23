package com.project.mystudyproject

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.animation.AnimationHomeActivity
import com.project.mystudyproject.databinding.ActivityMainBinding
import com.project.mystudyproject.notification.home.NotificationHomeActivity
import com.project.mystudyproject.view.CustomViewHomeActivity
import com.project.mystudyproject.view.MaskFilterActivity

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
            R.id.btn_custom_view ->
                startActivity(Intent(this, CustomViewHomeActivity::class.java))
            R.id.btn_mask_filter -> {
                startActivity(Intent(this, MaskFilterActivity::class.java))
            }
        }
    }
}