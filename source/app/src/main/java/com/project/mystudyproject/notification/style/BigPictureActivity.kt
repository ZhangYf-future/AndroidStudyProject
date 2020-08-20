package com.project.mystudyproject.notification.style

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityBigPictureBinding
import com.project.mystudyproject.notification.style.utils.SendNotificationUtils

/**
 * 在通知中添加大图片
 */
class BigPictureActivity : BaseActivity<ActivityBigPictureBinding>() {

    //渠道id
    private val mChannelId = "BigPicture"
    private val mChannelName = "大图通知"

    override fun getLayoutId(): Int = R.layout.activity_big_picture

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.btn_big_picture)
            showSimpleBigPicture()
        else if (view.id == R.id.btn_expand_big_picture)
            showExpandBigPicture()
    }

    //创建一个包含基本大图片的Notification
    private fun showSimpleBigPicture() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            this.setSmallIcon(R.drawable.frank)
            this.setContentTitle("大图通知")
            this.setContentText("显示大图的通知")
            this.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.frank))
            )
        }
        //创建渠道
        val utils = SendNotificationUtils(0x001)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }

    //创建一个在展开时才显示为大图的通知
    private fun showExpandBigPicture() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setContentTitle("大图通知")
            setContentText("展开时显示大图")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.frank))
                    .bigLargeIcon(null)
            )
        }
        //创建渠道并显示通知
        val utils = SendNotificationUtils(0x002)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }
}