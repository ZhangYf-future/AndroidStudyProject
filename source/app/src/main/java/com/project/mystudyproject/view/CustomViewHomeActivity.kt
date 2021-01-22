package com.project.mystudyproject.view

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityCustomViewHomeBinding
import com.project.mystudyproject.view.cutom_view.SimpleCustomViewActivity
import com.project.mystudyproject.view.test_path.TestPathActivity
import com.project.mystudyproject.view.test_rect.TestRectActivity
import com.project.mystudyproject.view.test_text.TestTextActivity

/**
 * 自定义View首页
 */
class CustomViewHomeActivity : BaseActivity<ActivityCustomViewHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_custom_view_home

    override fun doClick(view: View) {
        super.doClick(view)

        when (view.id) {
            R.id.btn_simple_custom_view_1 ->
                startActivity(Intent(this, SimpleCustomViewActivity::class.java))

            R.id.btn_test_rect ->
                startActivity(Intent(this, TestRectActivity::class.java))

            R.id.btn_test_path ->
                startActivity(Intent(this, TestPathActivity::class.java))

            R.id.btn_test_text ->
                startActivity(Intent(this, TestTextActivity::class.java))
        }
    }
}