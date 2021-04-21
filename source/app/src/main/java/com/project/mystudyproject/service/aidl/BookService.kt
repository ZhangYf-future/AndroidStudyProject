package com.project.mystudyproject.service.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.project.mystudyproject.Book
import com.project.mystudyproject.BookManager

class BookService : Service() {
    //数据列表
    private val mBookList = mutableListOf<Book>()

    private val mBookManagerStub by lazy {
        BookManagerStub()
    }

    override fun onCreate() {
        super.onCreate()
        //添加数据
        for (i in 0 until 10) {
            mBookList.add(Book("BookService-->$i", "${100 + i}"))
        }
    }


    override fun onBind(intent: Intent): IBinder = mBookManagerStub

    inner class BookManagerStub : BookManager.Stub() {
        override fun getBooks(): MutableList<Book> = mBookList

        override fun addBook(book: Book?) {
            book?.let {
                mBookList.add(it)
            }
        }

    }
}