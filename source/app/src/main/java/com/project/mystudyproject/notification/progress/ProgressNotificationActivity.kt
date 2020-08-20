package com.project.mystudyproject.notification.progress

import android.app.*
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityProgressNotificationBinding

class ProgressNotificationActivity : BaseActivity<ActivityProgressNotificationBinding>() {

    //通知id
    private val notificationId = 100

    //渠道id
    private val channelId = "NewChannel"

    //通知构造器
    private lateinit var mNotificationBuilder: NotificationCompat.Builder

    //发送延时消息的Handler
    private val mHandler by lazy {
        Handler()
    }

    override fun getLayoutId(): Int = R.layout.activity_progress_notification

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_send_progress -> {
                mHandler.removeCallbacksAndMessages(null)
                sendConfirmTimeNotification()
            }
            R.id.btn_send_no_progress -> {
                mHandler.removeCallbacksAndMessages(null)
                sendNotConfirmTimeNotification()
            }
        }
    }

    //发送确定时间的通知
    private fun sendConfirmTimeNotification() {
        createNotificationBuilder()
        var progress = 0
        mNotificationBuilder.setProgress(100, progress, false)
        showNotification()
        //启动一个Handler发送延迟消息
        mHandler.postDelayed(SendProgressRunnable(progress), 1000)
    }

    //发送不确定时间的通知
    private fun sendNotConfirmTimeNotification() {
        createNotificationBuilder()
        mNotificationBuilder.setProgress(0, 0, true)
        showNotification()
    }

    //显示通知
    private fun showNotification() {
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, mNotificationBuilder.build())
        }
    }

    //创建一个NotificationBuilder
    private fun createNotificationBuilder() {
        mNotificationBuilder = NotificationCompat.Builder(this, channelId)
        //设置小图标
        mNotificationBuilder.setSmallIcon(R.drawable.frank)
        //设置大图标
        mNotificationBuilder.setLargeIcon(
            BitmapFactory.decodeResource(
                resources,
                R.mipmap.ic_launcher
            )
        )
        //设置标题
        mNotificationBuilder.setContentTitle("App下载")
        //设置内容
        mNotificationBuilder.setContentText("正在下载")
        //设置优先级为默认
        mNotificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        //设置可删除
        mNotificationBuilder.setAutoCancel(true)

        //在8.0上创建通知渠道
        createChannelInfo()
    }

    //创建一个通道信息,在Android8.0上需要
    private fun createChannelInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //通道名称
            val channelName = "MyProjectChannelName"
            //通道说明
            val channelDes = "通道说明"
            //设置重要性为LOW  这样就不会有声音和震动了
            val importance = NotificationManager.IMPORTANCE_LOW
            //设置通道信息
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = channelDes
            }
            //将通道信息设置到通知栏管理服务
            val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    //发送延时消息的任务
    inner class SendProgressRunnable(
        private var progress: Int
    ) : Runnable {
        override fun run() {
            progress += 10
            mNotificationBuilder.setProgress(
                100,
                progress,
                false
            )
            showNotification()
            if (progress == 100) {
                NotificationManagerCompat.from(this@ProgressNotificationActivity)
                    .cancel(notificationId)
                mHandler.removeCallbacksAndMessages(null)
            } else {
                mHandler.postDelayed(this, 1000)
            }
        }

    }


}