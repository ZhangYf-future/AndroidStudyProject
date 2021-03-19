package com.project.mystudyproject.glide

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityGlideStudyHomeBinding

class GlideStudyHomeActivity : BaseActivity<ActivityGlideStudyHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_glide_study_home

    override fun initUI() {
        super.initUI()
        val bitmap: Bitmap? = null
        Glide.with(this)
            .load("http://www.xinhuanet.com/photo/2021-03/09/1127188521_16152558081601n.jpg")
            .placeholder(ColorDrawable(Color.BLACK))
            .error(ColorDrawable(Color.RED))
            .fallback(ColorDrawable(Color.GREEN))
            .thumbnail(0.1f)
            .centerCrop()
            .into(mBinding.image)
    }
}