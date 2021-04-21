package com.project.mystudyproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: Book
 * @Author: zyf
 * @Date: 2021/3/31 19:35
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
public class Book implements Parcelable {

    private String name;
    private String price;


    public Book() {

    }

    public Book(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Book(Parcel dest) {
        name = dest.readString();
        price = dest.readString();
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        price = dest.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
