package com.project.mystudyproject.activity.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLifecycleMethod2Binding

class ActivityLifecycleMethod2Activity : BaseActivity<ActivityLifecycleMethod2Binding>() {

    override fun getLayoutId(): Int = R.layout.activity_lifecycle_method2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logs.tagE("secondActivity: onCreate...")
    }

    override fun onStart() {
        super.onStart()
        Logs.tagE("secondActivity: onStart...")
    }

    override fun onResume() {
        super.onResume()
        Logs.tagE("secondActivity: onResume...")
    }

    override fun onRestart() {
        super.onRestart()
        Logs.tagE("secondActivity: onRestart...")
    }

    override fun onPause() {
        super.onPause()
        Logs.tagE("secondActivity: onPause...")
    }

    override fun onStop() {
        super.onStop()
        Logs.tagE("secondActivity: onStop...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logs.tagE("secondActivity: onDestroy...")
    }

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_back_first_activity ->
                onBackPressed()
        }
    }

}