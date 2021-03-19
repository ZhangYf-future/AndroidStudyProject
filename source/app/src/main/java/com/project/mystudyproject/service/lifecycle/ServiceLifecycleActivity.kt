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
import com.project.mystudyproject.databinding.ActivityServiceLifecycleBinding

/**
 * 测试服务生命周期的页面
 */
class ServiceLifecycleActivity : BaseActivity<ActivityServiceLifecycleBinding>() {

    private val ID = this.javaClass.simpleName

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logs.e(ID, "服务连接成功")
            service?.let { server ->
                if (server is LifecycleTest1Service.LifeCycleTest1Bind) {
                    var number = server.getService().getTestNumber()
                    Logs.e(ID, "number is $number")
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logs.e(ID, "服务断开连接")
        }
    }

    private val mConnection2 = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logs.e(ID, "服务2连接成功")
            service?.let { server ->
                if (server is LifecycleTest1Service.LifeCycleTest1Bind) {
                    var number = server.getService().getTestNumber()
                    Logs.e(ID, "number 2 is $number")
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logs.e(ID, "服务2断开连接")
        }
    }


    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_service_lifecycle

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_start_service_with_start -> {
                //通过startService的方式开启一个服务
                val intent = Intent(this, LifecycleTest1Service::class.java)
                intent.putExtra("key", "传递的信息")
                startService(intent)
            }

            R.id.btn_stop_service_with_stop -> {
                val intent = Intent(this, LifecycleTest1Service::class.java)
                stopService(intent)
            }

            R.id.btn_start_service_with_bind -> {
                //通过bindService绑定服务
                val bindIntent = Intent(this, LifecycleTest1Service::class.java)
                bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_unbind_service_1 -> {
                unbindService(mConnection)
            }

            R.id.btn_start_service_with_bind2 -> {
                //通过bindService绑定服务
                val bindIntent = Intent(this, LifecycleTest1Service::class.java)
                bindService(bindIntent, mConnection2, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_unbind_service_2 -> {
                unbindService(mConnection2)
            }

            R.id.btn_to_next_activity -> {
                startActivity(Intent(this, ServiceLifecycle2Activity::class.java))
            }
        }
    }

    override fun onDestroy() {
        unbindService(mConnection)
        unbindService(mConnection2)
        stopService(Intent(this, LifecycleTest1Service::class.java))
        super.onDestroy()
    }
}