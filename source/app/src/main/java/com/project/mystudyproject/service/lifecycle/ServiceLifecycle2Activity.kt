package com.project.mystudyproject.service.lifecycle

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityLifecycleTest2Binding

class ServiceLifecycle2Activity : BaseActivity<ActivityLifecycleTest2Binding>() {

    private val ID = this.javaClass.simpleName

    private val mConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logs.e(ID,"服务连接成功")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logs.e(ID,"连接断开")
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_lifecycle_test2

    override fun doClick(view: View) {
        super.doClick(view)
        when(view.id){
            R.id.btn_start_service_with_bind -> {
                val intent = Intent(this,LifecycleTest1Service::class.java)
                bindService(intent,mConnection,Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }
}