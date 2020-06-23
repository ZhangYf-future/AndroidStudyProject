package com.hopechart.baselib.net.download

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 *@time 2020/4/28
 *@user 张一凡
 *@description 下载文件的API
 *@introduction
 */
interface DownloadApi {
    @GET
    @Streaming
    fun downloadFile(@Url path: String): Call<ResponseBody>
}