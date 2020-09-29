package com.project.mystudyproject.notification.group

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityGroupNotificationBinding

/**
 * 创建一组通知的页面
 */
class GroupNotificationActivity : BaseActivity<ActivityGroupNotificationBinding>() {

    //渠道id
    private val mChannelId = "notificationGroup"

    //渠道名称
    private val mChannelName = "通知组"

    //通知组的key
    private val mNotificationGroupKey = "TEST_NOTIFICATION_GROUP"

    private var mId = 100

    private val mUtils by lazy {
        NotificationUtils(0x007)
    }

    override fun getLayoutId(): Int = R.layout.activity_group_notification

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_send -> {
                showNotification()
            }
            R.id.add_notification -> {
                addNotification()
            }
        }
    }

    //创建一组通知
    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("通知组")
            setContentText("一组通知")
            setGroup(mNotificationGroupKey)
        }

        val builder1 = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("通知组hahah")
            setContentText("一组通知haha")
            setGroup(mNotificationGroupKey)
        }

        val summaryNotification = NotificationCompat.Builder(this, mChannelId).apply {
            setContentTitle("新消息")
            setContentText("有新消息")
            setSmallIcon(R.drawable.frank)
            setGroup(mNotificationGroupKey)
            setGroupSummary(true)
        }

        mUtils.createChannel(mChannelId, mChannelName)
        //mUtils.showNotification(0x009, builder.build())
        //mUtils.showNotification(0x0010, builder1.build())
        mUtils.showNotification(0x0011, summaryNotification.build())
    }

    //添加一组新的通知
    private fun addNotification() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setContentTitle("哈哈哈$mId")
            setContentText("又一组新的通知$mId")
            setGroup(mNotificationGroupKey)
        }
        mUtils.createChannel(mChannelId, mChannelName)
        mUtils.showNotification(mId++, builder.build())
    }
}