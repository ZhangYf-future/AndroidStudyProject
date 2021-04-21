package com.project.mystudyproject.service.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.project.mystudyproject.*

/**
 * @作者 zyf
 * @日期 2021/4/1
 * @说明 测试异步AIDL的service
 * @修改信息
 */
class AsyncAidlService : Service() {

    private var mBookResultListener: BookBasicResult? = null

    private val mBinder by lazy {
        object : BookBasicManager.Stub() {
            override fun connect(listener: BookBasicResult?) {
                mBookResultListener = listener
            }

            override fun disconnect() {
                mBookResultListener = null
            }

            override fun getAllBooks() {
                getBookList()
            }

            override fun addBook(book: Book?) {
                book?.let {
                    this@AsyncAidlService.addBook(it)
                }
            }

        }
    }


    override fun onBind(intent: Intent): IBinder = mBinder

    //创建一个新的线程获取全部书籍列表
    private fun getBookList() {
        object : Thread() {
            override fun run() {
                super.run()
                sleep(10 * 1000)
                val list = mutableListOf<Book>()
                for (i in 0 until 10) {
                    list.add(Book("书籍$i", "${100 + i}"))
                }
                mBookResultListener?.getList(list)
            }
        }.start()
    }

    //创建一个新的线程添加一本书
    private fun addBook(book: Book) {
        object : Thread() {
            override fun run() {
                super.run()
                sleep(5 * 1000)
                mBookResultListener?.addBook(false)
            }
        }.start()
    }
}