// BookManager.aidl
package com.project.mystudyproject;

// Declare any non-default types here with import statements
import com.project.mystudyproject.Book;
interface BookManager {

    List<Book> getBooks();

    void addBook(in Book book);
}