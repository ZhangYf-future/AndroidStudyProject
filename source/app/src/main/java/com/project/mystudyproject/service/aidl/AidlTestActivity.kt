package com.project.mystudyproject.service.aidl

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.*
import com.project.mystudyproject.databinding.ActivityAidlTestBinding

class AidlTestActivity : BaseActivity<ActivityAidlTestBinding>() {

    val ID = "AIDL"

    //是否成功连接服务
    private var mConnectServiceSuccess = false


    //连接service的对象
    private val connect by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mConnectServiceSuccess = true
                Logs.e(ID, "service is $service")
                BookManager.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                mConnectServiceSuccess = false
            }

        }
    }


    //操作书籍的服务是否连接成功
    private var mBookServiceSuccess: Boolean = false

    //操作书籍的接口
    private var mBookManager: BookBasicManager? = null

    //书籍操作完成后的接口回调
    private val mBookResultListener by lazy {
        BookResultListener()
    }

    private val mBookServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Logs.e("异步书籍处理Service连接成功")
                mBookServiceSuccess = true
                mBookManager = BookBasicManager.Stub.asInterface(service)
                mBookManager?.connect(mBookResultListener)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                mBookManager?.disconnect()
                mBookServiceSuccess = false
            }
        }
    }

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_aidl_test

    //点击事件
    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_connect_service -> connectService()

            R.id.btn_get_all_books -> {
                mBookManager?.getAllBooks()
            }

            R.id.btn_add_book -> {
                val book = Book("要添加的书籍", "50")
                mBookManager?.addBook(book)
            }

        }
    }

    //连接服务
    private fun connectService() {

        //连接第一个服务
        val intent = Intent("BookService")
        intent.setPackage(this.packageName)
        bindService(intent, connect, Context.BIND_AUTO_CREATE)

        //连接书籍服务
        val bookIntent = Intent("com.project.mystudyproject.service.aidl.AsyncAidlService")
        bookIntent.setPackage(this.packageName)
        bindService(bookIntent, mBookServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (mConnectServiceSuccess)
            unbindService(connect)
        if (mBookServiceSuccess)
            unbindService(mBookServiceConnection)
        super.onDestroy()
    }


    inner class BookResultListener : BookBasicResult.Stub() {
        override fun getList(books: MutableList<Book>?) {
            //数据请求成功
            //这里会崩溃，因为这里目前还是在子线程中
            mBinding.btnGetAllBooks.text = "全部书籍$books"
        }

        override fun addBook(result: Boolean) {
            mBinding.btnAddBook.text = "添加书籍结果:$result"
        }

    }
}