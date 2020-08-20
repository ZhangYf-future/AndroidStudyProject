package com.project.mystudyproject.notification.style

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityMessagingStyleBinding
import com.project.mystudyproject.notification.style.utils.SendNotificationUtils

class MessagingStyleActivity : BaseActivity<ActivityMessagingStyleBinding>() {

    private val mChannelId = "MessagingNotification"
    private val mChannelName = "显示对话的通知"

    override fun getLayoutId(): Int = R.layout.activity_messaging_style

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.btn_send)
            showMessagingNotification()
    }

    //创建一个显示对话的通知
    private fun showMessagingNotification() {
        val person = Person.Builder().apply {
            setName("哈哈哈")
        }.build()
        //发送消息的用户
        val person1 = Person.Builder().apply {
            setName("好朋友")
        }.build()

        val person2 = Person.Builder().apply {
            setName("好孩子")
        }.build()
        //创建两个消息
        val message1 =
            NotificationCompat.MessagingStyle.Message("哈哈哈", System.currentTimeMillis(), person1)
        val message2 =
            NotificationCompat.MessagingStyle.Message("嘻嘻嘻", System.currentTimeMillis(), person2)
        val message3 =
            NotificationCompat.MessagingStyle.Message("汪往汪", System.currentTimeMillis(), person2)

        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setStyle(
                NotificationCompat.MessagingStyle(person)
                    .addMessage(message1)
                    .addMessage(message2)
                    .addMessage(message3)
                    .setConversationTitle("新的消息")
            )
        }

        //发送通知
        val utils = SendNotificationUtils(0x006)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }
}