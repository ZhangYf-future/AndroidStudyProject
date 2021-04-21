package com.project.mystudyproject

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.activity.ActivityStudyHomeActivity
import com.project.mystudyproject.animation.AnimationHomeActivity
import com.project.mystudyproject.databinding.ActivityMainBinding
import com.project.mystudyproject.glide.GlideStudyHomeActivity
import com.project.mystudyproject.jni_study.JniStudyHomeActivity
import com.project.mystudyproject.network.OkHttpStudyHomeActivity
import com.project.mystudyproject.notification.home.NotificationHomeActivity
import com.project.mystudyproject.rx_java_study.RxJavaStudyHomeActivity
import com.project.mystudyproject.service.ServiceHomeActivity
import com.project.mystudyproject.sp.SpActivity
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

            R.id.btn_glide_study -> {
                startActivity(Intent(this, GlideStudyHomeActivity::class.java))
            }
            R.id.btn_service_study -> {
                startActivity(Intent(this, ServiceHomeActivity::class.java))
            }

            R.id.btn_ok_http_study ->
                startActivity(Intent(this, OkHttpStudyHomeActivity::class.java))

            R.id.btn_rx_java_study ->
                startActivity(Intent(this, RxJavaStudyHomeActivity::class.java))

            R.id.btn_shared_preference_study ->
                startActivity(Intent(this, SpActivity::class.java))

            R.id.btn_activity_study ->
                startActivity(Intent(this, ActivityStudyHomeActivity::class.java))

            R.id.btn_jni_study ->
                startActivity(Intent(this, JniStudyHomeActivity::class.java))
        }
    }
}