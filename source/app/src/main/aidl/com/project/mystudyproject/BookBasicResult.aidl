// BookBasicResult.aidl
package com.project.mystudyproject;

// Declare any non-default types here with import statements

//BookBasicManager方法执行完成后的回调

import com.project.mystudyproject.Book;
interface BookBasicResult {

    //成功获取书籍列表
    void getList(out List<Book> books);

    //添加一本树
    void addBook(boolean result);
}