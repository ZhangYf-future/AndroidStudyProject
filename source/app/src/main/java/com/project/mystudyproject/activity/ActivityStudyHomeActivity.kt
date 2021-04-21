package com.project.mystudyproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.activity.lifecycle.ActivityLifecycleMethod1Activity
import com.project.mystudyproject.databinding.ActivityStudyHomeBinding

class ActivityStudyHomeActivity : BaseActivity<ActivityStudyHomeBinding>() {

    override fun initUI() {
        super.initUI()
        Logs.currentTag = getString(R.string.activity_study_tag)
    }

    override fun getLayoutId(): Int = R.layout.activity_study_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_lifecycle_method ->
                startActivity(Intent(this, ActivityLifecycleMethod1Activity::class.java))
        }
    }
}