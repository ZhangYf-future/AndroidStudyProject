package com.project.mystudyproject.service.intent_service

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityIntentServiceStudyBinding

class IntentServiceStudyActivity : BaseActivity<ActivityIntentServiceStudyBinding>() {

    //IntentService执行完成后通过广播返回数据
    private val mLocalBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val mReceiver by lazy {
        TestDataBackReceiver()
    }

    //是否成功绑定IntentService
    private var mBindIntentServiceSuccess = false

    //尝试绑定IntentService
    private val mServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mBindIntentServiceSuccess = true
                service?.let {
                    val binder = it as MyIntentService.MyIntentServiceBinder
                    binder.getService().mValueListener = object : IntentServiceValueListener {
                        override fun value(value: Any?) {
                            mBinding.btnStartIntentService.text = "接口接收到返回数据:$value"
                        }

                    }
                    for (i in 1 until 10) {
                        val intent = Intent()
                        intent.putExtra("key", "value$i")
                        binder.getService().onBind(intent)
                    }

                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Logs.e("服务连接断开")
                mBindIntentServiceSuccess = false
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLocalBroadcastManager.registerReceiver(
            mReceiver,
            IntentFilter(TestDataBackReceiver::class.simpleName)
        )
    }

    override fun onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mReceiver)
        if (mBindIntentServiceSuccess) {
            Logs.e("关闭服务连接")
            unbindService(mServiceConnection)
        }
        super.onDestroy()
    }

    override fun getLayoutId(): Int = R.layout.activity_intent_service_study

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_start_intent_service -> startIntentService()
            R.id.btn_bind_intent_service -> tryBindIntentService()

        }
    }

    //启动intentService
    private fun startIntentService() {

        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("key", "value0")
        startService(intent)

    }

    //尝试绑定intentService
    private fun tryBindIntentService() {
        for (i in 0 until 10) {
            val intent = Intent(this, MyIntentService::class.java)
            intent.putExtra("key", "value$i")
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    inner class TestDataBackReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val value = intent?.getStringExtra("key")
            mBinding.btnStartIntentService.text = "接收到返回数据:$value"
        }
    }

    //尝试通过接口回调数据
    interface IntentServiceValueListener {
        fun value(value: Any?)
    }
}