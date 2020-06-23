package com.hopechart.baselib

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.pinyinhelper.PinyinDict
import com.github.promeg.pinyinhelper.PinyinMapDict
import com.hopechart.baselib.utils.AppManager
import com.hopechart.baselib.utils.SharedPreferencesUtils

/**
 *@time 2020/4/15
 *@user 张一凡
 *@description
 *@introduction
 */
open class BaseApplication: Application() {
    companion object {
        private var instance: BaseApplication? = null

        @JvmStatic
        fun getInstance(): BaseApplication{
            if(instance == null)
                throw IllegalStateException("BaseApplication尚未初始化")
            else {
                return instance as BaseApplication
            }
        }
    }

    open lateinit var cookieExpireListener : CookieExpireListener

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //关闭在小米9等手机上出现的错误弹框
        closeAndroidPDialog()
        //初始化AppManager
        AppManager.getInstance().init(this)
        //初始化SharedPreferences
        SharedPreferencesUtils.init(this)
    }

    //关闭在小米9等手机上出现的错误弹框
    private fun closeAndroidPDialog() {
        try {
            val aClass =
                Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor =
                aClass.getDeclaredConstructor(
                    String::class.java
                )
            declaredConstructor.setAccessible(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod =
                cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown =
                cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface CookieExpireListener{
        fun cookieExpire()
    }

}