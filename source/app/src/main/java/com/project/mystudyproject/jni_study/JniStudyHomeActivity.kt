package com.project.mystudyproject.jni_study

import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityJniStudyHomeBinding

class JniStudyHomeActivity : BaseActivity<ActivityJniStudyHomeBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_jni_study_home

    override fun initUI() {
        super.initUI()
        mBinding.tvContent.text = JniTools.getStringFromNDK()
    }
}