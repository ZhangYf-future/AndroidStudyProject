package com.project.mystudyproject.service.intent_service

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hopechart.baselib.utils.Logs
import java.io.File
import java.util.logging.Handler

class MyIntentService : IntentService("MyIntentService") {

    private val mLocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val mBinder by lazy {
        MyIntentServiceBinder()
    }

    var mValueListener: IntentServiceStudyActivity.IntentServiceValueListener? = null

    override fun onHandleIntent(intent: Intent?) {
        //线程休眠
        Thread.sleep(1 * 1000)
        intent?.let {
            val value = it.getStringExtra("key")
            Logs.e("value is $value")
//            val newIntent = Intent(intent)
//            newIntent.action = IntentServiceStudyActivity.TestDataBackReceiver::class.simpleName
//            mLocalBroadcastManager.sendBroadcast(newIntent)
            mValueListener?.value(value)
        }
    }


    override fun onCreate() {
        Logs.e("onCreate...")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logs.e("onStartCommand...")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        Logs.e("onDestroy...")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        super.onStart(intent, 0)
        return mBinder

    }

    inner class MyIntentServiceBinder : Binder() {
        fun getService(): MyIntentService = this@MyIntentService
    }


}