// BookBasicManager.aidl
package com.project.mystudyproject;

// Declare any non-default types here with import statements
import com.project.mystudyproject.BookBasicResult;
import com.project.mystudyproject.Book;
interface BookBasicManager {

    //服务连接
    void connect(BookBasicResult listener);
    //服务断开连接
    void disconnect();
    //获取全部的数据列表
    void getAllBooks();
    //添加一本书
    void addBook(in Book book);
}