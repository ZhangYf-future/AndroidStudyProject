package com.hopechart.baselib.net

import android.text.TextUtils
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.cache.UserInfoCache
import okhttp3.*

/**
 *@time 2020/5/14
 *@user 张一凡
 *@description
 *@introduction
 */
class BaseUrlChangeInterceptor : Interceptor {
    companion object {
        const val CHANGE_URL = "useZQBaseUrl"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        val headers = request.headers
        val list = headers.values(CHANGE_URL)
        if (list.size == 1) {
            //替换baseUrl
            if (!TextUtils.isEmpty(UserInfoCache.getInstance().cacheUser.cranecloudBaseUrl)) {
                Logs.e("本地保存的重起地址:${UserInfoCache.getInstance().cacheUser.cranecloudBaseUrl}")
                Logs.e("本地保存的重起的Cookie：${UserInfoCache.getInstance().cacheUser.craneSessionId}")
                return chain.proceed(getNewRequest(request))
            } else {
                throw IllegalStateException("未能获取到缓存的用户信息，无法替换baseUrl")
            }
        }
        return chain.proceed(request)
    }


    //根据之前的request生成一个新的request
    private fun getNewRequest(oldRequest: Request): Request {
        val newRequest =oldRequest.newBuilder()
            .url(getNewUrl(oldRequest.url))
            .addHeader(
                "Cookie", "SHIRO_SESSION_ID=" +
                        UserInfoCache.getInstance().cacheUser.craneSessionId
            )
            .build()
        return newRequest
    }

    //根据用户信息设置一个新的URL
    private fun getNewUrl(oldUrl: HttpUrl): String {
        var newUrl = UserInfoCache.getInstance().cacheUser.cranecloudBaseUrl

        //如果新的的地址最后不是以"/"结束，就加上
        if (!TextUtils.equals("/", newUrl.last().toString())) {
            newUrl += "/"
        }

        var result = oldUrl.toString()
        if(oldUrl.toString().contains(NetConfig.getApi())){
            result = oldUrl.toString().replace(NetConfig.getApi(),newUrl)
        }
        Logs.e("最后拼接的地址$result")
        return result
    }
}