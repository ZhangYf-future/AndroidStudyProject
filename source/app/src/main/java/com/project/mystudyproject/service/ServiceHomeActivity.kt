package com.project.mystudyproject.service

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityServiceHomeBinding
import com.project.mystudyproject.service.lifecycle.ServiceLifecycleActivity

/**
 * Service学习首页
 */
class ServiceHomeActivity : BaseActivity<ActivityServiceHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_service_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_test_service_lifecycle -> {
                val intent = Intent(this, ServiceLifecycleActivity::class.java)
                startActivity(intent)
            }
        }
    }

}