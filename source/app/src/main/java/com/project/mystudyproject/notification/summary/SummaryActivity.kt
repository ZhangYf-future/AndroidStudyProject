package com.project.mystudyproject.notification.summary

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.SdkVersionUtils
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySummaryBinding
import com.project.mystudyproject.notification.group.NotificationUtils

/**
 * 通知栏基础操作总结
 */
class SummaryActivity : BaseActivity<ActivitySummaryBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_summary

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_send_basic_notification ->
                sendBasicNotification()
            R.id.btn_test_light -> {
                Handler().apply {
                    this.postDelayed({
                        sendBasicNotification()
                    }, 3000)
                }
            }
            R.id.btn_test_create_channel_group ->
                createChannelGroup()

            R.id.btn_test_notification_group ->
                createNotificationGroup()

            R.id.add_test_notification_group ->
                addTestNotificationGroup()

        }
    }

    //发送一个基本的通知栏消息
    val channelId = "basicChannelId"
    private fun sendBasicNotification() {
        //首先创建通知渠道

        if (SdkVersionUtils.checkAndroidO()) { //这个工具类只是判断当前的SDK版本是否大于等于8.0
            val channelName = "基本的通知栏消息渠道"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "这个渠道下的消息都是为了演示基本通知栏消息用的"
            channel.lightColor = Color.GREEN
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.setShowBadge(true)
            channel.vibrationPattern = longArrayOf(0, 5000, 5000)
            //channel.setAllowBubbles(true)
            val channelManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            channelManager.createNotificationChannel(channel)
            val readChannel = channelManager.getNotificationChannel(channelId)
            val vibrate = readChannel.shouldShowLights()
            Logs.e("呼吸灯:$vibrate")
        }
        //接下来创建通知
        val builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.drawable.frank) //smallIcon是必须的
        builder.setContentTitle("基本通知栏消息")
        builder.setContentText("这是一条基本的通知栏消息")
        builder.setLights(Color.GREEN, 0, 1000)
        builder.setNumber(10)
        builder.setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
        builder.setAutoCancel(true)
        //builder.setVibrate(longArrayOf(2000,1000))
        builder.priority = NotificationCompat.PRIORITY_HIGH
        val notificationInfo = builder.build()
        notificationInfo.flags = Notification.DEFAULT_LIGHTS

        //最后发送通知
        val notificationId = 1000
        NotificationManagerCompat.from(this).notify(notificationId, notificationInfo)
    }

    //创建渠道分组
    private fun createChannelGroup() {
        if (SdkVersionUtils.checkAndroidO()) {
            //分组id和名称
            //163邮箱的
            val CHANNEL_GROUP_ID_163 = "channelGroupId163"
            val CHANNEL_GROUO_NAME_163 = "163邮箱"
            //outlook邮箱的
            val CHANNEL_GROUP_ID_OUTLOOK = "channelGroupIdOutlook"
            val CHANNEL_GROUO_NAME_OUTLOOK = "outlook邮箱"
            //阿里云邮箱的
            val CHANNEL_GROUP_ID_ALI = "channelGroupIdAli"
            val CHANNEL_GROUP_NAME_ALI = "阿里云邮箱"

            //创建渠道分组
            val channelGroup163 =
                NotificationChannelGroup(CHANNEL_GROUP_ID_163, CHANNEL_GROUO_NAME_163)
            val channelGroupOutlook =
                NotificationChannelGroup(CHANNEL_GROUP_ID_OUTLOOK, CHANNEL_GROUO_NAME_OUTLOOK)
            val channelGroupAli =
                NotificationChannelGroup(CHANNEL_GROUP_ID_ALI, CHANNEL_GROUP_NAME_ALI)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannelGroup(channelGroup163)
            manager.createNotificationChannelGroup(channelGroupOutlook)
            manager.createNotificationChannelGroup(channelGroupAli)

            //分别创建通知渠道,这里为了方便我直接循环创建
            val channels = mutableListOf<NotificationChannel>()
            for (i in 0 until 9) {
                val channelId = when (i) {
                    0, 1, 2 -> "channelId_163_$i"
                    3, 4, 5 -> "channelId_outlook_$i"
                    else -> "channelId_ali_$i"
                }
                val channelName = when (i) {
                    0, 1, 2 -> "channelName_163_$i"
                    3, 4, 5 -> "channelName_outlook_$i"
                    else -> "channelName_ali_$i"
                }
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.group = when (i) {
                    0, 1, 2 -> CHANNEL_GROUP_ID_163
                    3, 4, 5 -> CHANNEL_GROUP_ID_OUTLOOK
                    else -> CHANNEL_GROUP_ID_ALI
                }
                channels.add(channel)
            }
            manager.createNotificationChannels(channels)
        }
    }

    //创建通知分组
    val NOTIFICATION_GROUP_ID_VIDEO = "notificationGroupIdVideo"
    val CHANNEL_ID_VIDEO = "channelIdVideo"
    private fun createNotificationGroup() {
        //视频通知渠道

        val CHANNEL_NAME_VIDEO = "视频新闻"
        //视频信息的通知分组ID

        NotificationUtils(1000).createChannel(CHANNEL_ID_VIDEO, CHANNEL_NAME_VIDEO)
        //就像创建通知一样创建一个通知分组
        val groupBuilder = NotificationCompat.Builder(this, CHANNEL_ID_VIDEO)
        groupBuilder.setGroup(NOTIFICATION_GROUP_ID_VIDEO)
        groupBuilder.setSmallIcon(R.drawable.frank)
        groupBuilder.setContentTitle("视频新闻")
        groupBuilder.setGroupSummary(true)

        //创建一个通知
//        val videoNewsBuilder = NotificationCompat.Builder(this, CHANNEL_ID_VIDEO)
//        videoNewsBuilder.setSmallIcon(R.drawable.frank)
//        videoNewsBuilder.setContentTitle("新的视频新闻")
//        videoNewsBuilder.setContentText("有一条信息的视频新闻，请查看")
//        videoNewsBuilder.setAutoCancel(true)
//        videoNewsBuilder.setGroup(NOTIFICATION_GROUP_ID_VIDEO)
        //发出通知
        //NotificationManagerCompat.from(this).notify(1000, groupBuilder.build())

        //NotificationManagerCompat.from(this).notify(10002, videoNewsBuilder.build())
        NotificationManagerCompat.from(this).notify(1000, groupBuilder.build())
    }

    var mChannelId = 100;
    private fun addTestNotificationGroup(){
        val videoNewsBuilder = NotificationCompat.Builder(this, CHANNEL_ID_VIDEO)
        videoNewsBuilder.setSmallIcon(R.drawable.frank)
        videoNewsBuilder.setContentTitle("新的视频新闻")
        videoNewsBuilder.setContentText("有一条信息的视频新闻，请查看")
        videoNewsBuilder.setAutoCancel(true)
        videoNewsBuilder.setGroup(NOTIFICATION_GROUP_ID_VIDEO)
        NotificationManagerCompat.from(this).notify(mChannelId++, videoNewsBuilder.build())
    }
}