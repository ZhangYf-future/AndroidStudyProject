package com.project.mystudyproject.sp

import java.security.KeyException

/**
 * @ClassName: SpUtils
 * @Author: zyf
 * @Date: 2021/3/25 9:24
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
class SpUtils(val key: String) {

    companion object{
        //设置应用包名
        const val PACKAGE_NAME = "keyPackageName"

        const val USER_NAME = "keyUserName"
    }

    private val delegates = SpDelegates(key)
    var value by delegates
}