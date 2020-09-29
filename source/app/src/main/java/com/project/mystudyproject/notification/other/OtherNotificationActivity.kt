package com.project.mystudyproject.notification.other

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityOtherNotificationBinding

class OtherNotificationActivity : BaseActivity<ActivityOtherNotificationBinding>() {

    //通知ID
    private val mNotificationId: Int = 1000

    //通知渠道ID
    private val mChannelId: String = "otherNotificationChannel"

    //通知构造器
    private lateinit var mNotificationBuilder: NotificationCompat.Builder

    //延时Handler
    private val mHandler = Handler()

    override fun getLayoutId(): Int = R.layout.activity_other_notification

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_category -> {
                createNotificationBuilder("通知类型", "可设置通知类型以选择在勿扰模式如何显示")
                mNotificationBuilder.setCategory(NotificationCompat.CATEGORY_EVENT)
                showNotification()
            }
            R.id.btn_important -> {
                createNotificationBuilder("紧急通知", "将紧急通知关联全屏Activity显示紧急事件")
                //mNotificationBuilder.setCategory(NotificationCompat.CATEGORY_ALARM)
                //mNotificationBuilder.setContentIntent(createFullScreenIntent())
                mNotificationBuilder.setFullScreenIntent(createFullScreenIntent(),true)
                mHandler.postDelayed({
                    showNotification()
                }, 5000)
            }
        }
    }


    //创建一个通知构造器
    private fun createNotificationBuilder(title: String, content: String) {
        mNotificationBuilder = NotificationCompat.Builder(this, mChannelId)
        mNotificationBuilder.setSmallIcon(R.drawable.frank)
        mNotificationBuilder.setContentTitle(title)
        mNotificationBuilder.setContentText(content)
        mNotificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        mNotificationBuilder.setAutoCancel(true)
        mNotificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL)
        createChannelInfo()
    }

    //创建渠道信息，在Android8.0需要
    private fun createChannelInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                mChannelId,
                "otherChannel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "其它类型的通知"
            val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //显示通知
    private fun showNotification() {
        with(NotificationManagerCompat.from(this)) {
            notify(mNotificationId, mNotificationBuilder.build())
        }
    }


    //创建一个全屏Intent
    private fun createFullScreenIntent(): PendingIntent {
        val intent = Intent(this, ImportantActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //创建任务连
        val taskBuilder = TaskStackBuilder.create(this)
        taskBuilder.addNextIntentWithParentStack(intent)
        return taskBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//        return PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        }
}