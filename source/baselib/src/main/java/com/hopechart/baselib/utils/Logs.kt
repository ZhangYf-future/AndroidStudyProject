package com.hopechart.baselib.utils

import android.util.Log
import com.hopechart.baselib.BaseApplication

/**
 *@time 2020/4/15
 *@user 张一凡
 *@description 打印日志类
 *@introduction
 */
class Logs {

    companion object {

        @JvmStatic
        fun e(tag: String, content: String){
            e(tag,content,false)
        }

        @JvmStatic
        fun e(content: String){
            val tag = BaseApplication.getInstance().packageName
            e(tag,content)
        }

        /**
         * @param tag: 日志标签
         * @param content: 日志内容
         * @param everyWhere: 在何处打印日志，默认只有在非release模式下才会打印日志
         */
        @JvmStatic
        fun e(tag: String,content: String,everyWhere: Boolean){
            if(everyWhere){
                Log.e(tag,content)
            }else if(ConfigUtils.isDebug){
                Log.e(tag,content)
            }
        }
    }
}