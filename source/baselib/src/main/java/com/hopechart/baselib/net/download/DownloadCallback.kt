package com.hopechart.baselib.net.download

import java.io.File

/**
 *@time 2020/4/28
 *@user 张一凡
 *@description 下载文件回调接口
 *@introduction
 */
interface DownloadCallback {
    //下载出错
    fun onDownloadError(message: String)
    //下载完成
    fun onDownloadComplete(file: File,url: String)
    //下载进度
    fun onDownloadProgress(progress: Double)
    //开始下载
    fun onDownloadStart()
    //加载进度,total 总的加载数据  progress 当前加载的数据
    fun onLoading(total: Long,progress: Long)
    //文件转存中
    fun onTransferSave(progress: Double)
}