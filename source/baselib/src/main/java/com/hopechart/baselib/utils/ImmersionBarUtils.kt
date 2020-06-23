package com.hopechart.baselib.utils

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.hopechart.baselib.R

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
class ImmersionBarUtils {

    companion object {

        //白色状态栏
        fun whiteStatusBar(activity: Activity, fits: Boolean) {
            setBarColor(activity, null, fits, R.color.white, true)
        }

        //白色状态栏 - Fragment
        fun whiteStatusBar(fragment: Fragment, fits: Boolean) {
            setBarColor(null, fragment, fits, R.color.white, true)
        }

        //指定颜色的状态栏
        fun setStatusBar(activity: Activity, fits: Boolean, color: Int, statusBarDark: Boolean) {
            setBarColor(activity, null, fits, color, statusBarDark)
        }

        fun setStatusBar(fragment: Fragment, fits: Boolean, color: Int, statusBarDark: Boolean) {
            setBarColor(null, fragment, fits, color, statusBarDark)
        }


        //设置状态栏颜色
        private fun setBarColor(
            activity: Activity?,
            fragment: Fragment?,
            fits: Boolean,
            color: Int,
            statusBarDark: Boolean
        ) {
            val immersion =
                if (activity != null) ImmersionBar.with(activity) else if (fragment != null) ImmersionBar.with(
                    fragment
                ) else null

            if (immersion != null) {
                immersion.statusBarColor(color)
                    .fitsSystemWindows(fits)

                if (statusBarDark) {
                    if (ImmersionBar.isSupportStatusBarDarkFont())
                        immersion.statusBarDarkFont(true)
                    else
                        immersion.statusBarDarkFont(true, 0.2f)
                } else {
                    immersion.statusBarDarkFont(false)
                }
                immersion.init()
            }
        }

        //透明状态栏
        fun tranStatusBar(activity: Activity, fits: Boolean) {
            ImmersionBar.with(activity)
                .statusBarColor(R.color.transparent)
                .fitsSystemWindows(fits)
                .init()
        }

        //Fragment透明状态栏
        fun tranStatusBar(fragment: Fragment,fits: Boolean){
            ImmersionBar.with(fragment)
                .statusBarColor(R.color.transparent)
                .fitsSystemWindows(fits)
                .init()
        }

        //透明状态栏，黑色文字
        fun transStatusBarDark(activity: Activity, fits: Boolean) {
            val immersion = ImmersionBar.with(activity)
            if (immersion != null) {
                immersion.statusBarColor(R.color.transparent)
                    .fitsSystemWindows(fits)
                if (ImmersionBar.isSupportStatusBarDarkFont())
                    immersion.statusBarDarkFont(true)
                else
                    immersion.statusBarDarkFont(true, 0.2f)
            }
            immersion.init()
        }

        //状态栏和内容重叠
        fun transBarWithHeight(fragment: Fragment,view: View){
            val immersion = ImmersionBar.with(fragment)
            immersion.statusBarColor(R.color.transparent)
            immersion.titleBarMarginTop(view)
            immersion.init()
        }

        //状态栏内容重叠
        fun transBarWithHeight(activity: Activity,view: View,darkFont: Boolean = false){
            val immersion = ImmersionBar.with(activity)
            immersion.statusBarColor(R.color.transparent)
            immersion.titleBarMarginTop(view)
            if(darkFont){
                immersion.statusBarDarkFont(true)
            }
            immersion.init()
        }
    }


}