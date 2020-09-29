package com.project.mystudyproject.notification.group

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.BaseApplication

class NotificationUtils(private val id: Int) {


    //Android8.0及以上配置此项
    fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "默认的渠道"
            val manager = BaseApplication.getInstance()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //显示通知
    fun showNotification(notificationId: Int,notification: Notification) {
        NotificationManagerCompat.from(BaseApplication.getInstance()).apply {
            notify(notificationId,notification)
        }
    }
}