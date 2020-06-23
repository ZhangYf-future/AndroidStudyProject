package com.hopechart.baselib.net.download

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers

/**
 *@time 2020/4/29
 *@user 张一凡
 *@description 下载文件的woker
 *@introduction
 */
class DownloadWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override val coroutineContext = Dispatchers.IO


    override suspend fun doWork(): Result {
        val url: String? = inputData.getString("downloadPath")
        if (url != null) {
            val downloadFileUtils = DownloadFileUtils(context, url, null, null, true)
            downloadFileUtils.download()
        } else {
            val data = workDataOf("error" to "下载地址为空")

            Result.failure(data)
        }

        return Result.success()
    }

}