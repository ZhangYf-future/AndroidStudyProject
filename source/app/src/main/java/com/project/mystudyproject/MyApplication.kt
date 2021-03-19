package com.project.mystudyproject

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.room.Room
import com.bumptech.glide.Glide
import com.hopechart.baselib.BaseApplication
import com.hopechart.baselib.db.AppDatabase

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        mAppDataBase = Room.databaseBuilder(this, AppDatabase::class.java, "test")
            .build()



    }


}