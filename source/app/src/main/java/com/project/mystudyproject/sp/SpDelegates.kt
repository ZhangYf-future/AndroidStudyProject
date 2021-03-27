package com.project.mystudyproject.sp

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.hopechart.baselib.BaseApplication
import com.hopechart.baselib.utils.Logs
import kotlin.reflect.KProperty

/**
 * @ClassName: SpDelegates
 * @Author: zyf
 * @Date: 2021/3/25 9:13
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
class SpDelegates(val key: String) {

    companion object {
        //文件名称
        private const val FILE_NAME = "user.config"
    }

    //获取SharedPreference
    private fun getSp(): SharedPreferences = BaseApplication.getInstance().getSharedPreferences(
        FILE_NAME, Context.MODE_PRIVATE
    )

    //获取某一个数据
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        val value = getSp().getString(key, "")
        Logs.e("getValue:$value")
        return value
    }

    //设置某一个数据
    operator fun setValue(spUtils: Any?, property: KProperty<*>, s: String?) {
        Logs.e("setValue...")
        if (!TextUtils.isEmpty(s)) {
            getSp().edit().putString(key, s).apply()
        }
    }
}