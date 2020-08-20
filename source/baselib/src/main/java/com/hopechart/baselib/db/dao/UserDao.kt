package com.hopechart.baselib.db.dao

import androidx.room.*
import com.project.data.UserSimpleEntity

/**
 * 用户基本信息数据库访问类
 */
@Dao
interface UserSimpleDao {
    //查询全部用户
    @Query("SELECT * FROM UserSimpleEntity")
    fun getAll(): List<UserSimpleEntity>

    //根据用户的uid查找用户信息
    @Query("SELECT * FROM UserSimpleEntity WHERE uid IN (:userIds)")
    fun getAllByIds(userIds: IntArray): List<UserSimpleEntity>

    //查找符合条件的用户信息
    @Query("SELECT * FROM UserSimpleEntity WHERE first_name LIKE :firstName AND last_name LIKE :lastName LIMIT 1")
    fun getUserByName(firstName: String, lastName: String): UserSimpleEntity

    //添加一个用户信息
    @Insert
    fun insertAll(vararg users: UserSimpleEntity)

    //删除一个用户信息
    @Delete
    fun delete(user: UserSimpleEntity)

    //更新一个用户信息
    @Update
    fun update(user: UserSimpleEntity)
}