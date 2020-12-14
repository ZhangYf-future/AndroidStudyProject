package com.project.mystudyproject.animation.property

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.animation.property.transition.LayoutTransitionActivity
import com.project.mystudyproject.databinding.ActivityPropertyAnimationHomeBinding

class PropertyAnimationHomeActivity : BaseActivity<ActivityPropertyAnimationHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_property_animation_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_basic_animator ->
                startActivity(Intent(this, BasicActivity::class.java))
            R.id.btn_use_animator_show_or_hide_view ->
                startActivity(Intent(this, ShowOrHideViewActivity::class.java))
            R.id.btn_use_animator_move ->
                startActivity(Intent(this, MoveAnimatorActivity::class.java))
            R.id.btn_layout_change_add_animation ->
                startActivity(Intent(this, LayoutTransitionActivity::class.java))
        }
    }

}