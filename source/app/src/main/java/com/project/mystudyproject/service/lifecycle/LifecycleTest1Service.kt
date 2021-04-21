package com.project.mystudyproject.service.lifecycle

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import com.hopechart.baselib.utils.Logs

/**
 * 测试服务生命周期的Service
 */
class LifecycleTest1Service : Service() {

    val ID = this.javaClass.simpleName


    private var testNumber = 0

    private val mHandler = Handler()

    private val mRunnable = object : Runnable {
        override fun run() {
            Logs.e(ID, "发送消息")
            //mHandler.postDelayed(this, 5 * 1000)
            //stopSelf()
            testNumber++
        }
    }

    private val mBinder = LifeCycleTest1Bind()

    override fun onTaskRemoved(rootIntent: Intent?) {
        Logs.e(ID, "onTaskRemoved")
        super.onTaskRemoved(rootIntent)
    }

    override fun onCreate() {
        Logs.e(ID, "onCreate")
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Logs.e(ID, "onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logs.e(ID, "onStartCommand")
        intent?.let {
            Logs.e(ID, it.getStringExtra("key"))
        }
        mHandler.postDelayed(mRunnable, 5 * 1000)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Logs.e(ID, "onBind")
        //返回null以表示该服务不支持绑定
        return mBinder
    }

    override fun onLowMemory() {
        Logs.e(ID, "onLowMemory")
        super.onLowMemory()
    }

    /**
     * 当服务启动以后，点击home键返回到桌面会调用这个方法
     */
    override fun onTrimMemory(level: Int) {
        Logs.e(ID, "onTrimMemory:$level")
        super.onTrimMemory(level)
    }


    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        Logs.e(ID, "onDestroy")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logs.e(ID, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Logs.e(ID,"onStart...")
    }

    fun getTestNumber() = testNumber

    inner class LifeCycleTest1Bind : Binder() {
        //在此处返回对当前Service的引用
        fun getService(): LifecycleTest1Service = this@LifecycleTest1Service
    }
}