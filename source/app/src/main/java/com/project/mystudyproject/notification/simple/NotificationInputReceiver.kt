package com.project.mystudyproject.notification.simple

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.core.app.RemoteInput
import com.hopechart.baselib.utils.Logs

class NotificationInputReceiver: BroadcastReceiver() {

    private val mHandler = Handler()

    override fun onReceive(context: Context?, intent: Intent?) {
        Logs.e("接收到消息")
        context?.let {

            //获取用户的输入
            val input = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_replay")
            Logs.e("用户的输入为:$input")

            mHandler.postDelayed({
                Logs.e("清空通知")
                val manager: NotificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(100)
            },1000)

        }
    }
}