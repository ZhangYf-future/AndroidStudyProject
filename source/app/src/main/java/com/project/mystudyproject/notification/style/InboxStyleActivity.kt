package com.project.mystudyproject.notification.style

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityInboxStyleBinding
import com.project.mystudyproject.notification.style.utils.SendNotificationUtils

/**
 * 多行通知
 */
class InboxStyleActivity : BaseActivity<ActivityInboxStyleBinding>() {

    private val mChannelId: String = "inboxStyle"
    private val mChannelName: String = "inboxStyleNotification"

    override fun getLayoutId(): Int = R.layout.activity_inbox_style

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.btn_more_line)
            showSimpleNotification()
    }

    //创建通知
    private fun showSimpleNotification() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("收件箱样式的通知")
            setContentText("这是一个收件箱样式的通知，使用InboxStyle")
            setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行第一行")
                    .addLine("第二行")
                    .addLine("第三行")
                    .addLine("第四行")
                    .addLine("第五行")
                    .addLine("第六行")
                    .addLine("第七行")
                    .addLine("第八行")
                    .addLine("第九行")
                    .addLine("第十行")
                    .addLine("第十一行")
            )
        }
        //发送通知
        val utils = SendNotificationUtils(0x006)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }
}