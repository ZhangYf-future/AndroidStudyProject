package com.project.mystudyproject.notification.style

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityMediaStyleBinding
import com.project.mystudyproject.notification.style.utils.SendNotificationUtils

class MediaStyleActivity : BaseActivity<ActivityMediaStyleBinding>() {

    private val mChannelId = "MediaStyleNotification"
    private val mChannelName = "媒体样式的通知"

    override fun getLayoutId(): Int = R.layout.activity_media_style

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.btn_send)
            showMediaNotification()
    }


    //创建通知并发送
    private fun showMediaNotification() {

        val preIntent = PendingIntent.getActivity(
            this,
            0x001,
            Intent(this, MessagingStyleActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setContentTitle("媒体通知")
            setContentText("这是一个媒体样式的通知")
            addAction(R.mipmap.ic_back_black, "上一首", preIntent)
            addAction(R.mipmap.ic_search_white,"暂停",preIntent)
            addAction(R.mipmap.ic_back_white,"暂停",preIntent)
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0,1,2)
            )
            //设置此属性以让通知再锁屏界面显示完整信息
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            color = Color.parseColor("#4574fa")
        }
        //发送通知
        val utils = SendNotificationUtils(0x007)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }
}