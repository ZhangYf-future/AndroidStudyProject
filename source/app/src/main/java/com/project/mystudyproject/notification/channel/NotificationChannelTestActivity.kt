package com.project.mystudyproject.notification.channel

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.Settings
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.SdkVersionUtils
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityNotificationChannelTestBinding

class NotificationChannelTestActivity : BaseActivity<ActivityNotificationChannelTestBinding>() {


    companion object {
        //强提醒的渠道id
        const val HIGH_ALARM_CHANNEL_ID = "highAlarmChannelId"

        //强提醒的通知名称
        const val HIGH_ALARM_CHANNEL_NAME = "强提醒通知"

        //弱提醒的渠道id
        const val LOW_ALARM_CHANNEL_ID = "lowAlarmChannelId"

        //弱提醒的通知名称
        const val LOW_ALARM_CHANNEL_NAME = "弱提醒通知"

        //渠道分组 -- 工作账号
        const val CHANNEL_GROUP_WORK_ID = "channelGroupWithWork"
        const val CHANNEL_GROUP_WORK_NAME = "工作账号下的渠道分组"

        //渠道分组 -- 个人账号
        const val CHANNEL_GROUP_PERSON_ID = "channelGroupWithPerson"
        const val CHANNEL_GROUP_PERSON_NAME = "个人账号下的渠道分组"

        //工作账号下接收新消息的通知渠道
        const val WORK_CHANNEL_RECEIVE_MESSAGE_ID = "workChannelReceiveMessageId"
        const val WORK_CHANNEL_RECEIVE_MESSAGE_NAME = "工作账号下接收新消息的通知渠道"

        //工作账号下发送消息的通知渠道
        const val WORK_CHANNEL_SEND_MESSAGE_ID = "workChannelSendMessageId"
        const val WORK_CHANNEL_SEND_MESSAGE_NAME = "工作账号下发送消息的通知渠道"

        //个人账号下接收消息的通知渠道
        const val PERSON_CHANNEL_RECEIVE_MESSAGE_ID = "personChannelReceiveMessageId"
        const val PERSON_CHANNEL_RECEIVE_MESSAGE_NAME = "个人账号下接收新消息的通知渠道"

        //个人账号下发送消息的通知渠道
        const val PERSON_CHANNEL_SEND_MESSAGE_ID = "personChannelSendMessageId"
        const val PERSON_CHANNEL_SEND_MESSAGE_NAME = "个人账号下发送消息的通知渠道"

    }

    override fun getLayoutId(): Int = R.layout.activity_notification_channel_test

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_high_alarm_channel ->
                createHighAlarmChannel()
            R.id.send_high_notification ->
                createHighNotification()
            R.id.btn_low_alarm_channel ->
                createLowAlarmChannel()
            R.id.send_low_notification ->
                createLowNotification()
            R.id.btn_read_high_notification_channel ->
                readNotificationInfo()
            R.id.btn_open_notification_channel_setting ->
                openNotificationChannelSetting()
            R.id.btn_create_channel_group ->
                createChannelGroup()
            R.id.btn_create_channel_test_channel_group ->
                createChannelTestChannelGroup()
            R.id.btn_send_work_receive_message_notification ->
                sendWorkReceiveMessageNotification()
        }
    }

    //创建一个强提醒的通知渠道
    private fun createHighAlarmChannel() {
        if (SdkVersionUtils.checkAndroidO()) {
            val channel =
                NotificationChannel(
                    HIGH_ALARM_CHANNEL_ID,
                    HIGH_ALARM_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.description = "这个渠道的提醒是强通知提醒，会尽可能打扰用户"
            channel.setShowBadge(true)
            val channelManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channelManager.createNotificationChannel(channel)
        }
    }

    //创建一个弱提醒的通知渠道
    private fun createLowAlarmChannel() {
        if (SdkVersionUtils.checkAndroidO()) {
            val channel =
                NotificationChannel(
                    LOW_ALARM_CHANNEL_ID,
                    LOW_ALARM_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                )
            channel.description = "这个渠道的通知是弱提醒通知，会尽可能不打扰用户"
            val channelManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channelManager.createNotificationChannel(channel)
        }
    }

    //创建一个强提醒的通知
    private fun createHighNotification() {
        val builder = NotificationCompat.Builder(this, HIGH_ALARM_CHANNEL_ID)
        builder.setSmallIcon(R.drawable.frank)
        builder.setContentTitle("强提醒")
        builder.setContentText("这是一个强提醒的通知")
        builder.setNumber(10)
        NotificationManagerCompat.from(this).notify(100, builder.build())
    }

    //创建一个弱提醒的通知
    private fun createLowNotification() {
        val builder = NotificationCompat.Builder(this, LOW_ALARM_CHANNEL_ID)
        builder.setSmallIcon(R.drawable.frank)
        builder.setContentTitle("弱提醒")
        builder.setContentText("这是一个弱提醒通知")
        NotificationManagerCompat.from(this).notify(101, builder.build())
    }

    //读取某一个通知渠道的信息
    private fun readNotificationInfo() {
        if (SdkVersionUtils.checkAndroidO()) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //获取指定的通知渠道
            val channel = manager.getNotificationChannel(HIGH_ALARM_CHANNEL_ID)
            Logs.e("获取到的通道信息:$channel")
            val info = StringBuilder("渠道信息:\n")
            info.append("id:${channel.id}\n")
            info.append("name:${channel.name}\n")
            info.append("importance:${channel.importance}\n")
            info.append("description:${channel.description}\n")
            info.append("audioAttributes:" + channel.audioAttributes + "\n")
            info.append("group:${channel.group} \n")
            info.append("lightColor:${channel.lightColor}\n")
            info.append("lockscreenVisibility:${channel.lockscreenVisibility}\n")
            info.append("sound:${channel.sound}\n")
            info.append("vibrationPattern：${channel.vibrationPattern}\n")
            info.append("canShowBadge${channel.canShowBadge()}\n")
            //这个方法在我的一加手机上使用的时候会报错，找不到这个方法
            //info.append("hasUserSetImportance: ${channel.hasUserSetImportance()}\n")
            //是否有闪光灯
            info.append("shouldShowLights: ${channel.shouldShowLights()} \n")
            //是否震动
            info.append("shouldVibrate:${channel.shouldVibrate()} \n")
            Logs.e("$info")
        }
    }

    //打开通知渠道设置页面
    private fun openNotificationChannelSetting() {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, HIGH_ALARM_CHANNEL_ID)
        startActivity(intent)
    }

    //创建渠道分组
    private fun createChannelGroup() {
        if (SdkVersionUtils.checkAndroidO()) {
            //创建用于工作账号的渠道分组
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val workChannelGroup =
                NotificationChannelGroup(CHANNEL_GROUP_WORK_ID, CHANNEL_GROUP_WORK_NAME)
            val personChannelGroup =
                NotificationChannelGroup(CHANNEL_GROUP_PERSON_ID, CHANNEL_GROUP_PERSON_NAME)
            notificationManager.createNotificationChannelGroup(workChannelGroup)
            notificationManager.createNotificationChannelGroup(personChannelGroup)
        }
    }

    //创建用于测试通知渠道分组的通知渠道
    private fun createChannelTestChannelGroup() {
        if (SdkVersionUtils.checkAndroidO()) {
            val workReceiveMessageChannel = NotificationChannel(
                WORK_CHANNEL_RECEIVE_MESSAGE_ID,
                WORK_CHANNEL_RECEIVE_MESSAGE_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            workReceiveMessageChannel.description = "用于测试工作账号下接收消息的通知渠道"
            workReceiveMessageChannel.group = CHANNEL_GROUP_WORK_ID

            val workSendMessageChannel = NotificationChannel(
                WORK_CHANNEL_SEND_MESSAGE_ID,
                WORK_CHANNEL_SEND_MESSAGE_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            workSendMessageChannel.description = "用于测试工作账号下发送消息的通知渠道"
            workSendMessageChannel.group = CHANNEL_GROUP_WORK_ID

            val personReceiveMessageChannel = NotificationChannel(
                PERSON_CHANNEL_RECEIVE_MESSAGE_ID,
                PERSON_CHANNEL_RECEIVE_MESSAGE_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            personReceiveMessageChannel.description = "用于测试个人账号下接收消息的通知渠道"
            personReceiveMessageChannel.group = CHANNEL_GROUP_PERSON_ID

            val personSendMessageChannel = NotificationChannel(
                PERSON_CHANNEL_SEND_MESSAGE_ID,
                PERSON_CHANNEL_SEND_MESSAGE_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            personSendMessageChannel.description = "用于测试个人账号下发送消息的通知渠道"
            personSendMessageChannel.group = CHANNEL_GROUP_PERSON_ID

            val channels: MutableList<NotificationChannel> = mutableListOf(
                workReceiveMessageChannel,
                workSendMessageChannel,
                personReceiveMessageChannel,
                personSendMessageChannel
            )
            val notificationManage =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManage.createNotificationChannels(channels)
        }
    }

    //发送一条通知 --  工作账号下接收到新消息的通知
    private fun sendWorkReceiveMessageNotification() {
        val notification = NotificationCompat.Builder(this, WORK_CHANNEL_RECEIVE_MESSAGE_ID)
        notification.setContentTitle("工作账号收到新消息")
        notification.setContentText("工作账号收到一条新消息")
        notification.setSmallIcon(R.drawable.frank)
        notification.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
        notification.priority = NotificationManagerCompat.IMPORTANCE_DEFAULT
        NotificationManagerCompat.from(this).notify(10000, notification.build())
    }
}