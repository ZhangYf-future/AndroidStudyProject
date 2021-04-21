package com.project.mystudyproject.activity.lifecycle

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLifecycleMethod1Binding

class ActivityLifecycleMethod1Activity : BaseActivity<ActivityLifecycleMethod1Binding>() {

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_lifecycle_method1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logs.tagE("secondActivity: onCreate...")
    }

    override fun onStart() {
        super.onStart()
        Logs.tagE("firstActivity: onStart...")
    }

    override fun onResume() {
        super.onResume()
        Logs.tagE("firstActivity: onResume...")
    }

    override fun onRestart() {
        super.onRestart()
        Logs.tagE("firstActivity: onRestart...")
    }

    override fun onPause() {
        super.onPause()
        Logs.tagE("firstActivity: onPause...")
    }

    override fun onStop() {
        super.onStop()
        Logs.tagE("firstActivity: onStop...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logs.tagE("firstActivity: onDestroy...")
    }

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_to_second_activity ->
                startActivity(Intent(this, ActivityLifecycleMethod2Activity::class.java))
        }
    }
}