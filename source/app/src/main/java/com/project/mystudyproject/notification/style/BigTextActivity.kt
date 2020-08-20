package com.project.mystudyproject.notification.style

import android.graphics.BitmapFactory
import android.os.Build
import android.text.Html
import android.view.View
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.ui.BaseActivity
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityBigTextNotificationBinding
import com.project.mystudyproject.notification.style.utils.SendNotificationUtils

class BigTextActivity : BaseActivity<ActivityBigTextNotificationBinding>() {

    //渠道信息
    private val mChannelId = "bigTextChannel"
    private val mChannelName = "大段文本通知"

    override fun getLayoutId(): Int = R.layout.activity_big_text_notification

    override fun doClick(view: View) {
        super.doClick(view)
        if (view.id == R.id.btn_big_text)
            showSimpleBigText()
        else if (view.id == R.id.btn_more_style_big_text)
            showMoreStyleBigText()
    }

    //创建通知
    private fun showSimpleBigText() {
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setContentTitle("大段文本")
            setContentText("显示大段文本通知")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getString(R.string.big_text))
            )
        }
        //创建渠道并发送通知
        val utils = SendNotificationUtils(0x003)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }

    //创建多样式文本通知
    private fun showMoreStyleBigText() {
        val text = getString(R.string.more_style_big_text)
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(text)
        mBinding.tvResult.text = result
        //创建通知
        val builder = NotificationCompat.Builder(this, mChannelId).apply {
            setSmallIcon(R.drawable.frank)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.frank))
            setContentTitle("多样式文本")
            setContentText("这里是多样式文本，通过Html转换的文本")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(result)
                    .setSummaryText("summaryText")
                    .setBigContentTitle("BigContentTitle")
            )
        }
        //发送通知
        val utils = SendNotificationUtils(0x004)
        utils.createSimpleChannel(mChannelId, mChannelName)
        utils.showNotification(builder.build())
    }
}