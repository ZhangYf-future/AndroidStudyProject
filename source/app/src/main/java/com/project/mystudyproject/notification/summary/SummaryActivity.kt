package com.project.mystudyproject.notification.summary

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.TaskStackBuilder
import androidx.core.graphics.drawable.IconCompat
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.SdkVersionUtils
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySummaryBinding
import com.project.mystudyproject.notification.channel.NotificationChannelTestActivity
import com.project.mystudyproject.notification.style.MessagingStyleActivity
import com.project.mystudyproject.notification.utils.ChannelUtils
import com.project.mystudyproject.notification.utils.NotificationUtils


/**
 * 通知栏基础操作总结
 */
class SummaryActivity : BaseActivity<ActivitySummaryBinding>() {

    private val receiver by lazy {
        TestMediaStyleBroadcastReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter(TestMediaStyleBroadcastReceiver::class.java.name)
        registerReceiver(receiver, filter)


        val intent = Intent()
        intent.action = TestMediaStyleBroadcastReceiver()::class.java.name
        intent.putExtra("2113","21321")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

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
            R.id.add_test_to_Activity ->
                toActivity()
            R.id.test_to_activity_with_task_stack_builder ->
                toActivityWithTaskStackBuilder()
            R.id.test_to_special_activity ->
                toSpecialActivity()
            R.id.test_big_picture_notification ->
                createBigPictureNotification()
            R.id.test_big_text_notification ->
                createBigTextNotification()
            R.id.test_inbox_notification ->
                createInboxStyleNotification()
            R.id.test_messaging_notification ->
                createMessagingStyleNotification()
            R.id.test_media_notification ->
                createMediaStyleNotification()

        }
    }

    //发送一个基本的通知栏消息
    val channelId = "basicChannelId"
    private fun sendBasicNotification() {
        //首先创建通知渠道
        if (SdkVersionUtils.checkAndroidO()) { //这个工具类只是判断当前的SDK版本是否大于等于8.0
            val channelName = "基本的通知栏消息渠道"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
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
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        val notificationInfo = builder.build()
        notificationInfo.flags = Notification.DEFAULT_LIGHTS

        //最后发送通知
        val notificationId = 1000
        NotificationManagerCompat.from(this).notify(notificationId, notificationInfo)
    }

    //分组id和名称
    //163邮箱的
    private val CHANNEL_GROUP_ID_163 = "channelGroupId163"
    private val CHANNEL_GROUO_NAME_163 = "163邮箱"

    //outlook邮箱的
    private val CHANNEL_GROUP_ID_OUTLOOK = "channelGroupIdOutlook"
    private val CHANNEL_GROUO_NAME_OUTLOOK = "outlook邮箱"

    //阿里云邮箱的
    private val CHANNEL_GROUP_ID_ALI = "channelGroupIdAli"
    private val CHANNEL_GROUP_NAME_ALI = "阿里云邮箱"

    //创建渠道分组
    private fun createChannelGroup() {
        if (SdkVersionUtils.checkAndroidO()) {


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

    val NOTIFICATION_GROUP_KEY_VIDEO = "notificationGroupIdVideo"//通知分组的唯一标识
    val SUMMARY_GROUP_ID = 0 //通知组摘要ID 也就是显示这个通知组的时候的ID
    private fun createNotificationGroup() {
        //第一条消息
//        val newMessage1 = NotificationCompat.Builder(this, "channelId_163_0").apply {
//            setSmallIcon(R.drawable.frank)
//            setContentTitle("第一条消息")
//            setContentText("这里是第一条消息")
//            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
//            setGroup(NOTIFICATION_GROUP_KEY_VIDEO)
//        }.build()
//        //第二条消息
//        val newMessage2 = NotificationCompat.Builder(this, "channelId_163_0").apply {
//            setSmallIcon(R.drawable.frank)
//            setContentTitle("第二条新消息")
//            setContentText("这里第二条新消息")
//            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
//            setGroup(NOTIFICATION_GROUP_KEY_VIDEO)
//
//        }.build()
        //分组摘要
        val summaryNotification = NotificationCompat.Builder(this, "channelId_163_0").apply {
            setContentTitle("通知分组测试")
            setContentText("两条新消息")
            setSmallIcon(R.drawable.frank)
            setStyle(
                NotificationCompat.InboxStyle()
                    .setBigContentTitle("两条新消息")
                    .setSummaryText("摘要内容")
            )
                .setGroup(NOTIFICATION_GROUP_KEY_VIDEO)
                .setGroupSummary(true)
        }.build()
        NotificationManagerCompat.from(this).apply {
//            notify(100, newMessage1)
//            notify(101, newMessage2)
            notify(SUMMARY_GROUP_ID, summaryNotification)
        }

    }

    var mNotifyId = 105
    private fun addTestNotificationGroup() {
        val videoNewsBuilder = NotificationCompat.Builder(this, "channelId_163_0")
        videoNewsBuilder.setSmallIcon(R.drawable.frank)
        videoNewsBuilder.setContentTitle("新的视频新闻")
        videoNewsBuilder.setContentText("有一条信息的视频新闻，请查看")
        videoNewsBuilder.setAutoCancel(true)
        videoNewsBuilder.setGroup(NOTIFICATION_GROUP_KEY_VIDEO)
        NotificationManagerCompat.from(this).notify(mNotifyId++, videoNewsBuilder.build())
        //createNotificationGroup()
    }

    //测试跳转一个普通的Activity
    private fun toActivity() {
        //创建渠道
        createToActivityChannel()
        //构建需要跳转到的Activity
        val intent = Intent(this, NotificationChannelTestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //创建通知消息
        val notification = NotificationCompat.Builder(this, mToActivityChannel).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("跳转Activity测试")
            setContentText("用于测试是否能够跳转到Activity")
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()
        //发送通知
        NotificationManagerCompat.from(this).notify(100, notification)
    }

    //创建一个通知渠道
    private val mToActivityChannel = "testToActivityNotificationChannel"
    private fun createToActivityChannel() {
        if (SdkVersionUtils.checkAndroidO()) {
            val name = "测试跳转页面的通知渠道"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(mToActivityChannel, name, importance)
            channel.description = "这个渠道用来测试发出通知消息后点击通知栏消息能否跳转到指定页面"
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //使用TaskStackBuilder构建PendingIntent
    private fun toActivityWithTaskStackBuilder() {
        //渠道信息
        val channelId = "testTaskStackBuilder"
        val channelName = "测试返回任务栈"
        val channelDescription = "用于测试返回任务栈的通知栏消息渠道"
        //创建渠道
        if (SdkVersionUtils.checkAndroidO()) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelDescription
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        //通知信息
        val notificationId = 100
        //构建要跳转的页面
        val intent = Intent(this, TestTaskStackBuilderActivity2::class.java)
        val taskBuilder = TaskStackBuilder.create(this).apply {
            this.addParentStack(TestTaskStackBuilderActivity2::class.java)
            this.addNextIntent(intent)
        }
        val pendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        //构建通知
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("测试返回栈")
            setContentText("测试任务返回栈的消息")
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)
        }
        Handler().postDelayed({
            //发送通知
            NotificationManagerCompat.from(this).notify(notificationId, builder.build())
        }, 5000)
    }

    //跳转到特殊的Activity
    private fun toSpecialActivity() {
        //渠道信息
        val channelId = "testSpecialActivity"
        val channelName = "测试跳转到特殊的Activity"
        val channelDescription = "用于测试跳转到特殊Activity的通知栏消息渠道"
        //创建渠道
        if (SdkVersionUtils.checkAndroidO()) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = channelDescription
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        //通知信息
        val notificationId = 100
        //构建要跳转的页面
        val intent = Intent(this, SpecialActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //构建通知
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("测试跳转特殊Activity")
            setContentText("只有通过点击通知栏消息才能跳转到的Activity")
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)
        }
        //发送通知
        NotificationManagerCompat.from(this).notify(notificationId, builder.build())

    }


    //创建用于测试Style的通知渠道
    private val testStyleChannelId = "testStyleChannel"
    private fun createTestStyleChannel() {
        val name = "测试通知style"
        val utils = ChannelUtils.getInstance(this)
        utils.createChannelWithIdName(testStyleChannelId, name)
    }

    //创建用于测试Style的通知分组
    private val testStyleNotificationGroupKey = "testStyleGroupKey"
    private fun createTestStyleNotificationGroup() {
        val title = "测试通知样式的分组"
        val content = "用于测试通知样式的分组"
        val utils = NotificationUtils.getInstance(this)
        val builder = utils.createNotificationGroup(
            testStyleChannelId,
            testStyleNotificationGroupKey,
            R.drawable.frank,
            title,
            content,
            null
        )
        utils.sendNotificationGroup(builder.build())
    }

    //显示大图片的通知
    private fun createBigPictureNotification() {
        //创建通道
        createTestStyleChannel()
        //创建通知
        val builder = NotificationCompat.Builder(this, testStyleChannelId)
        builder.setSmallIcon(R.drawable.frank)
        builder.setContentTitle("大图通知")
        builder.setContentText("大图通知")
        builder.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
                .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.frank))
                .setSummaryText("二级标题")
        )
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setAutoCancel(true)
        builder.setGroup(testStyleNotificationGroupKey)
        //显示通知
        NotificationManagerCompat.from(this).notify(100, builder.build())
        //创建并显示通知分组
        createTestStyleNotificationGroup()
    }

    //显示大段文本通知
    private fun createBigTextNotification() {
        //创建一个渠道
        createTestStyleChannel()
        //通知相关的信息
        val title = "大段文本通知"
        val content = "测试大段文本的通知"
        //创建大段文本通知的style
        val style = NotificationCompat.BigTextStyle().run {
            this.bigText("这里是大段通知文本这里是大段通知文本这里是大段通知文本这里是大段通知文本这里是大段通知文本")
            this.setSummaryText("用于测试大段文本通知的style")
        }

        val notificationUtils = NotificationUtils.getInstance(this)
        //创建通知实体
        val builder = notificationUtils
            .createNotification(
                testStyleChannelId,
                R.drawable.frank,
                title,
                content,
                style,
                null,
                testStyleNotificationGroupKey
            )
        //发送通知
        notificationUtils.sendNotification(builder.build())
        //创建并发送通知分组
        createTestStyleNotificationGroup()
    }


    //创建收件箱样式的通知
    private fun createInboxStyleNotification() {
        //创建渠道
        createTestStyleChannel()
        //通知内容
        val title = "邮件"
        val content = "收到新邮件"
        val style = NotificationCompat.InboxStyle().run {
            this.addLine("第一条简介")
            this.addLine("第二条简介")
            this.addLine("第三条简介")
        }
        //创建消息
        val utils = NotificationUtils.getInstance(this)
        val builder = utils.createNotification(
            testStyleChannelId,
            R.drawable.frank,
            title,
            content,
            style,
            null,
            testStyleNotificationGroupKey
        )
        //发送消息
        utils.sendNotification(builder.build())
        //创建并发送通知分组
        createTestStyleNotificationGroup()
    }

    //在通知中显示对话
    private fun createMessagingStyleNotification() {
        //创建渠道
        createTestStyleChannel()
        //通知消息实体
        val title = "联系人"
        val content = "联系人发来新消息"
        //创建发送消息用户的信息
        val user = Person.Builder().run {
            this.setIcon(IconCompat.createWithResource(this@SummaryActivity, R.drawable.ic_icon1))
            this.setName("Bob")
        }
        //创建style
        val style = NotificationCompat.MessagingStyle(user.build()).run {
            //添加消息
            this.addMessage(
                "Hello World",
                System.currentTimeMillis() - 1000 * 60 * 60 * 24,
                user.build()
            )
            this.addMessage(
                "Hello World",
                System.currentTimeMillis() - 1000 * 60 * 60 * 24,
                user.build()
            )
            this.addMessage(
                NotificationCompat.MessagingStyle.Message(
                    "Thank you",
                    System.currentTimeMillis(),
                    user.build()
                )
            )
        }
        //创建消息
        val utils = NotificationUtils.getInstance(this)
        val builder = utils.createNotification(
            testStyleChannelId,
            R.drawable.frank,
            title,
            content,
            style,
            null,
            testStyleNotificationGroupKey
        )
        //发送消息
        utils.sendNotification(builder.build())
        //创建并发送通知分组消息
        createTestStyleNotificationGroup()
    }

    //创建媒体控件通知
    private fun createMediaStyleNotification() {
        //创建渠道
        createTestStyleChannel()
        //创建消息实体
        //五个按钮对应的操作
        val operateAction = TestMediaStyleBroadcastReceiver::class.java.name
        //上一首
        val preIntent = Intent(operateAction)
        //preIntent.action = operateAction
        preIntent.putExtra(operateKey, "上一首")
        val prePendingIntent =
            PendingIntent.getBroadcast(this, 0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //下一首
        val nextIntent = Intent()
        nextIntent.action = operateAction
        nextIntent.putExtra(operateKey, "下一首")
        val nextPendingIntent =
            PendingIntent.getBroadcast(this, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //播放或者暂停
        val stateIntent = Intent()
        stateIntent.action = operateAction
        stateIntent.putExtra(operateKey, "播放/暂停")
        val statePendingIntent =
            PendingIntent.getBroadcast(this, 2, stateIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //收藏
        val collectionIntent = Intent()
        collectionIntent.action = operateAction
        collectionIntent.putExtra(operateKey, "收藏")
        val collectionPendingIntent =
            PendingIntent.getBroadcast(this, 3, collectionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //喜欢
        val likeIntent = Intent()
        likeIntent.action = operateAction
        likeIntent.putExtra(operateKey, "喜欢")
        val likePendingIntent =
            PendingIntent.getBroadcast(this, 4, likeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //创建消息实体
        val utils = NotificationUtils.getInstance(this)
        val style = androidx.media.app.NotificationCompat.MediaStyle().run {
            this.setShowActionsInCompactView(0, 1, 3)
        }
        val builder = utils.createNotification(
            testStyleChannelId,
            R.drawable.frank,
            R.drawable.frank,
            "My Music",
            "我的音乐",
            null,
            style,
            true,
            NotificationCompat.PRIORITY_DEFAULT,
            null
        )
        builder.addAction(R.mipmap.ic_back_black, "pre", prePendingIntent)
        builder.addAction(R.mipmap.ic_back_white, "state", statePendingIntent)
        builder.addAction(R.mipmap.ic_launcher, "next", nextPendingIntent)
        builder.addAction(R.mipmap.ic_search_white, "collection", collectionPendingIntent)
        builder.addAction(R.mipmap.ic_bg_layout_title, "like", likePendingIntent)
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        builder.color = Color.parseColor("#336699")
        //发送消息
        utils.sendNotification(1000, builder.build())
    }

    private val operateKey = "MyOperate"

    inner class TestMediaStyleBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("TAG", "获取到通知")
            Log.e("TAG", "操作:${intent?.getStringExtra(operateKey)}")
        }
    }

}