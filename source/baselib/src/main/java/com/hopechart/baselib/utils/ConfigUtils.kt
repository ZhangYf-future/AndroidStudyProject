package com.hopechart.baselib.utils

import com.hopechart.baselib.BuildConfig


/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
class ConfigUtils {
    companion object {
        //返回当前版本信息
        const val config: String = BuildConfig.BUILD_TYPE

        //不是release版本则为debug版本，因为还有可能包括debug,develop,test等版本
        const val isDebug = config != "release"


    }
}