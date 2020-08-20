package com.hopechart.baselib.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hopechart.baselib.db.dao.UserSimpleDao
import com.project.data.UserSimpleEntity

@Database(entities = [UserSimpleEntity::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    //用户基本信息操作类
    abstract fun userSimpleDao(): UserSimpleDao
}