package com.project.mystudyproject.notification.simple

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import kotlinx.android.synthetic.main.activity_simple_notification2.*

class SimpleNotification2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_notification2)
        val value = intent.getStringExtra("name")
        Logs.e("获取到传递的信息$value")
        tv_title.text = value

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(100)

    }
}