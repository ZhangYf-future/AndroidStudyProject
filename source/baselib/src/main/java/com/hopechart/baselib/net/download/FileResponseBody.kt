package com.hopechart.baselib.net.download

import com.hopechart.baselib.utils.Logs
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 *@time 2020/4/29
 *@user 张一凡
 *@description
 *@introduction
 */
class FileResponseBody
    (
    //实际的请求体
    private val mResponseBody: ResponseBody,

    //下载回调接口
    private val mCallback: DownloadCallback

): ResponseBody() {


    //BufferSource
    private val mBufferSource: BufferedSource by lazy {
        source(mResponseBody.source()).buffer()
    }



    override fun contentLength(): Long =mResponseBody.contentLength()

    override fun contentType(): MediaType? =mResponseBody.contentType()

    override fun source(): BufferedSource {
        return mBufferSource
    }

    private fun source(source: Source): Source{
        return object : ForwardingSource(source) {
            var totalByteRead = 0L
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalByteRead += if (bytesRead != -1L) bytesRead else 0
                mCallback.onLoading(mResponseBody.contentLength(), totalByteRead)
                mCallback.onDownloadProgress(totalByteRead.toDouble() / mResponseBody.contentLength().toDouble())
                return bytesRead
            }
        }
    }
}