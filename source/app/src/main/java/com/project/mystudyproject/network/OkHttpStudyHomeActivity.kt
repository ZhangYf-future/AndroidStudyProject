package com.project.mystudyproject.network

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityOkHttpStudyHomeBinding
import kotlin.properties.Delegates

/**
 * OkHttp学习首页
 */
class OkHttpStudyHomeActivity : BaseActivity<ActivityOkHttpStudyHomeBinding>() {

    private lateinit var mView: View

    private val name: String by lazy {
        "123"
    }

    private var value: Int by Delegates.observable(10) { property, oldValue, newValue ->
        Logs.e("value changed: $property,$oldValue,$newValue")
    }

    override fun getLayoutId(): Int = R.layout.activity_ok_http_study_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_ok_http_basic_usage ->
                startActivity(Intent(this, OkHttpBasicUsageActivity::class.java))
        }
    }

    override fun initUI() {
        super.initUI()
        if (::mView.isInitialized) {
            Logs.e("已经完成了初始化")
        } else {
            Logs.e("没有完成初始化")
        }

        Logs.e("value设置数据前:${value}")
        value = 100
        Logs.e("value设置数据后:$value")
    }
}