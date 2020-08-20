package com.project.mystudyproject.notification.home

import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityNotificationHomeBinding
import com.project.mystudyproject.notification.other.OtherNotificationActivity
import com.project.mystudyproject.notification.progress.ProgressNotificationActivity
import com.project.mystudyproject.notification.simple.SimpleNotificationActivity
import com.project.mystudyproject.notification.style.*

class NotificationHomeActivity : BaseActivity<ActivityNotificationHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_notification_home

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_show_simple_notification -> {
                startActivity(Intent(this, SimpleNotificationActivity::class.java))
            }
            R.id.btn_show_progress -> {
                startActivity(Intent(this, ProgressNotificationActivity::class.java))
            }
            R.id.btn_other_notification -> {
                startActivity(Intent(this, OtherNotificationActivity::class.java))
            }
            R.id.btn_big_picture_notification -> {
                startActivity(Intent(this, BigPictureActivity::class.java))
            }
            R.id.btn_big_text_notification -> {
                startActivity(Intent(this, BigTextActivity::class.java))
            }

            R.id.btn_more_line_notification -> {
                startActivity(Intent(this, InboxStyleActivity::class.java))
            }

            R.id.btn_messaging_notification -> {
                startActivity(Intent(this, MessagingStyleActivity::class.java))
            }
            R.id.btn_media_notification -> {
                startActivity(Intent(this, MediaStyleActivity::class.java))
            }
        }
    }
}