package com.project.mystudyproject.notification.simple

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.app.TaskStackBuilder
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySimpleNotificationBinding

class SimpleNotificationActivity : BaseActivity<ActivitySimpleNotificationBinding>() {

    private val mChannelId = "MyProject"

    override fun getLayoutId(): Int = R.layout.activity_simple_notification

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.send)
            sendNotification()
    }

    //发送通知
    private fun sendNotification() {
        val title = mBinding.etTitle.text.toString()
        val content = mBinding.etContent.text.toString()

        //设置点击通知栏跳转的页面
        //如果要跳转的页面只会在用户点击状态栏之后才会出现，那么就可以使用如下的方式设置PendingIntent
        //首先需要在Manifest文件中为需要跳转的Activity添加`taskAffinity=""`属性
        //同时添加`excludeFromRecents="true"`属性用于在用户点击最近应用列表时不会出现此Activity
        //在这里需要注意的是，使用了上面的属性的Activity,如果这个Activity正在显示，那么在最近的任务列表仍然是可以看到的
        //当用户点击了Home键或者使用其他方式进行了导航操作，再次进入到最近任务列表则不会显示。
        //创建可以启动Activity的Intent，并且设置相关参数，如下所示
        val intent = Intent(this, SimpleNotification4Activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //使用getActivity创建PendingIntent
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        //如果要跳转的页面本身就存在于应用正常的页面跳转流程中，则使用如下的方式配合在Manifest文件中的parentActivity属性实现页面的正常跳转流程
        val resultIntent = Intent(this, SimpleNotification2Activity::class.java)
        resultIntent.putExtra("name", "fromSimpleNotificationActivity")
        val resultPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //如果还需要在通知栏添加操作按钮，则可以进行如下设置
        //需要注意的是，一个通知栏最多提供三个操作按钮，通过添加action来进行操作
        //首先对intent添加action
        resultIntent.action = "cancel"


        //添加直接回复操作(Android7.0及以上支持)
        //创建一个可接受用户输入的文本框
        val KEY_TEXT_REPLAY = "key_text_replay"
        val replyLabel = "在此处输入回复内容"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLAY).run {
            setLabel(replyLabel)
            build()
        }
        //通知栏添加一个额外的输入按钮，点击此按钮可以打开上面的文本输入框
        val replyIntent = Intent(this,NotificationInputReceiver::class.java)
        replyIntent.action = "replyNotification"
        val replyPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            100,
            replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //将remote附加到操作对象上
        val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
            R.drawable.ic_no_full_screen,
            "回复", replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()


        val builder = NotificationCompat.Builder(this, mChannelId)
        //设置小图标
        builder.setSmallIcon(R.drawable.frank)
        //设置标题
        builder.setContentTitle(title)
        //设置文本
        builder.setContentText(content)
        //设置样式
        builder.setStyle(NotificationCompat.BigTextStyle().bigText("${content}这是展开的文本"))
        //设置优先级
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        //设置要跳转的页面
        builder.setContentIntent(pendingIntent)
        //为builder添加按钮操作
        builder.addAction(R.drawable.ic_no_full_screen, "取消", resultPendingIntent)
        //添加回复
        builder.addAction(replyAction)
        //在Android 10以上，平台会自动生成通知操作按钮，如果不希望平台自动生成，则设置以下两个方法取消自动生成
        builder.setAllowSystemGeneratedContextualActions(false)
        //允许用户自动移除通知
        builder.setAutoCancel(true)
        //在Android 8.0上需要设置通道信息
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "MyProjectChannelName"
            val channelDescriptionText = "通道说明"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(mChannelId, channelName, importance).apply {
                this.description = channelDescriptionText
            }
            //发送通知
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            notify(100, builder.build())
        }
    }

}