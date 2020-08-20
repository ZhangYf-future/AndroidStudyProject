package com.project.mystudyproject.notification.style.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.BaseApplication

/**
 * 发送通知的工具类
 * @param id 发送通知的id
 */
class SendNotificationUtils(private val id: Int) {


    //默认的渠道id
    private val mDefaultChannelId: String = "defaultChannelId"

    //默认的渠道名称
    private val mDefaultChannelName: String = "defaultChannelName"

    //默认的渠道重要性
    private val mDefaultImportance: Int = NotificationManager.IMPORTANCE_DEFAULT

    //默认的渠道说明
    private val mDefaultChannelDes: String = "用于显示默认的通知消息"

    //在Android8.0及以上版本创建渠道信息
    private fun createChannel(
        channelId: String? = null,
        channelName: String? = null,
        channelImportance: Int? = null,
        channelDes: String? = null
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId ?: mDefaultChannelId,
                    channelName ?: mDefaultChannelName,
                    channelImportance ?: mDefaultImportance
                )
            channel.description = channelDes ?: mDefaultChannelDes
            val manager = BaseApplication.getInstance()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //创建一个默认的渠道信息
    fun createDefaultChannel() = createChannel()

    //创建一个包含基本信息的渠道信息
    fun createSimpleChannel(id: String, name: String) = createChannel(id, name)

    //显示通知
    fun showNotification(notification: Notification) {
        val manager = BaseApplication.getInstance()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }
}