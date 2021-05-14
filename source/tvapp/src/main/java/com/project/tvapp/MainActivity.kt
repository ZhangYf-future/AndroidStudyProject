package com.project.tvapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.leanback.app.BrandedSupportFragment
import com.project.tvapp.fragment.brabded.BrandedFragmentStudyActivity
import com.project.tvapp.fragment.browse.BrowseFragmentActivity
import com.project.tvapp.fragment.rows.MyRowsSupportFragment
import com.project.tvapp.fragment.rows.RowsSupportFragmentStudyActivity
import com.project.tvapp.fragment.rows1.RowsSupportFragmentStudy1Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //默认第一个按钮获取焦点
        btn_study_branded_fragment.requestFocus()

        //点击进入到BrandedFragment学习页面
        btn_study_branded_fragment.setOnClickListener {
            startActivity(Intent(this, BrandedFragmentStudyActivity::class.java))
        }

        //点击进入到BrowseSupportFragment学习页面
        btn_study_browse_support_fragment.setOnClickListener {
            startActivity(Intent(this, BrowseFragmentActivity::class.java))
        }

        //点击进入到RowsSupportFragment学习页面
        btn_study_rows_support_fragment.setOnClickListener {
            startActivity(Intent(this, RowsSupportFragmentStudyActivity::class.java))
        }

        //点击进入到RowsSupportFragment学习页面1
        btn_study_rows_support_fragment1.setOnClickListener {
            startActivity(Intent(this, RowsSupportFragmentStudy1Activity::class.java))
        }
    }
}