package com.project.mystudyproject.network

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityOkHttpStudyHomeBinding

/**
 * OkHttp学习首页
 */
class OkHttpStudyHomeActivity : BaseActivity<ActivityOkHttpStudyHomeBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_ok_http_study_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_ok_http_basic_usage ->
                startActivity(Intent(this, OkHttpBasicUsageActivity::class.java))
        }
    }
}