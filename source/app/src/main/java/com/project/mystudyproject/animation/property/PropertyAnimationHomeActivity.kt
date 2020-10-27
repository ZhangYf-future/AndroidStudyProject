package com.project.mystudyproject.animation.property

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityPropertyAnimationHomeBinding

class PropertyAnimationHomeActivity : BaseActivity<ActivityPropertyAnimationHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_property_animation_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_basic_animator ->
                startActivity(Intent(this, BasicActivity::class.java))
        }
    }

}