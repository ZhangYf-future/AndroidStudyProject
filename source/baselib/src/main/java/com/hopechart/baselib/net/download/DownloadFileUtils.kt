package com.hopechart.baselib.net.download

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.hopechart.baselib.BaseApplication
import com.hopechart.baselib.R
import com.hopechart.baselib.net.NetConfig
import com.hopechart.baselib.utils.AppUtils
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.ToastUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.math.abs

/**
 *@time 2020/4/28
 *@user 张一凡
 *@description 文件下载工具类
 *@introduction
 */
class DownloadFileUtils(
    private val mContext: Context,
    private val mUrl: String,
    private val mCallback: DownloadCallback?
) {

    //当前回调
    private val mLocalCallback: DownloadCallback =
        object : DownloadCallback {
            override fun onDownloadError(message: String) {
                if (mCallback != null)
                    mCallback.onDownloadError(message)
                else
                    ToastUtils.showShort(message)
            }

            override fun onDownloadComplete(file: File, url: String) {
                if (mCallback != null) {
                    mCallback.onDownloadComplete(file, url)
                } else if (showNotification) {
                    Logs.e("文件下载完成:" + file.absolutePath)
                    if (file.name.endsWith(".apk"))
                        AppUtils.installApk(file, mContext)
                }
            }

            override fun onDownloadProgress(progress: Double) {
                if (mCallback != null)
                    mCallback.onDownloadProgress(progress)
                else if (showNotification) {
                    showNotification((progress * 100).toInt())
                }
            }

            override fun onDownloadStart() {
                mCallback?.onDownloadStart()
            }

            override fun onLoading(total: Long, progress: Long) {
                mCallback?.onLoading(total, progress)
            }

            override fun onTransferSave(progress: Double) {
                mCallback?.onTransferSave(progress)
                if (showNotification) {
                    showNotification(progress.toInt())
                }
            }

        }

    //保存文件的路径
    private var savePath: String? = null

    //是否默认在通知栏显示进度条
    private var showNotification: Boolean = false

    private val mDownloadApi: DownloadApi by lazy {
        NetConfig.createApi(DownloadApi::class.java)
    }

    constructor(
        context: Context,
        url: String,
        callback: DownloadCallback?,
        savePath: String?,
        notification: Boolean
    ) : this(context, url, callback) {
        this.savePath = savePath
        this.showNotification = notification
    }

    //开始下载文件
    fun download() {
        //判断权限是否存在
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mLocalCallback.onDownloadError("缺少文件权限，请检查权限后重试")
            return
        }
        //判断文件是否存在
        if (TextUtils.isEmpty(savePath)) {
            //存储可用
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                //获取应用目录
                savePath =
                    Environment.getDataDirectory()?.absolutePath + "/data/com.hopechart.zqy" + "/apk"
            } else {
                mLocalCallback.onDownloadError("存储未知不可用，无法下载")
                return
            }
        }
        val directory = File(savePath!!)
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Logs.e("$savePath 路径不存在，创建文件夹失败，请检查路径")
                mLocalCallback.onDownloadError("路径不存在，创建文件夹失败，无法下载文件，请检查")
                return
            }
        }

        //获取文件后缀名
        val index = mUrl.lastIndexOf("/")
        val fileName: String
        fileName = if (index == -1) {
            //未知文件名及类型
            Logs.e("未知文件名及类型")
            "UnKnow"
        } else {
            mUrl.substring(index, mUrl.length)
        }
        val resultFile = File(directory, fileName)
        //开始下载
        mLocalCallback.onDownloadStart()
        val call = getRetrofitService().downloadFile(mUrl)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Logs.e("下载异常:$t")

                mLocalCallback.onDownloadError("下载出错：$t")

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    //下载完成，开始转换数据
                    object : Thread() {
                        override fun run() {
                            super.run()

                            writeToDisk(response, resultFile)
                        }
                    }.start()
                } else {
                    mLocalCallback.onDownloadError("无法连接服务器：${response.code()}")
                }
            }

        })
    }

    //将下载到的数据存储在本地
    private fun writeToDisk(response: Response<ResponseBody>, file: File) {
        var currentLength = 0;
        val inputStream = response.body()?.byteStream()
        if (inputStream != null) {
            //输出到文件的流
            val outputStream: OutputStream = FileOutputStream(file)
            try {
                //从流中读出文件并写入到输出流中
                var bytes = ByteArray(1024)
                var len = inputStream.read(bytes)
                while (len != -1) {
                    outputStream.write(bytes, 0, len)
                    currentLength += len
                    len = inputStream.read(bytes)
                }

                //下载完成
                mLocalCallback.onDownloadComplete(file, mUrl)
                if (showNotification)
                //如果有显示通知栏，就清空通知栏信息
                    mNotificationManager.cancelAll()
            } catch (e: IOException) {
                mLocalCallback.onDownloadError("出现异常")
            } finally {
                inputStream.close()
                outputStream.close()
            }
        } else {
            Logs.e("输入流为空，无法下载")
            mLocalCallback.onDownloadError("下载出错，请稍后重试")
        }
    }


    //重新配置一个Retrofit,注意这里并没有使用之前配置的Retrofit
    private fun getRetrofitService(): DownloadApi {
        val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        httpBuilder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val response = chain.proceed(chain.request())

                return response.newBuilder()
                    .body(response.body?.let { FileResponseBody(it, mLocalCallback) }).build()
            }
        })
        val retrofit = Retrofit.Builder()
            .baseUrl(NetConfig.getApi())
            .client(httpBuilder.build())
            .build()
        return retrofit.create(DownloadApi::class.java)
    }


    //通知管理
    private val mMessageId = 1000 //默认消息id
    private val mNotificationId = "channel_zqy"
    private val mName = AppUtils.getAppName()

    //旧的进度条
    private var mOldProgress: Int = -1

    //通知栏管理器
    private val mNotificationManager by lazy {
        val context: Context = BaseApplication.getInstance()
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    //通知栏显示的View
    private val mDownloadProgressView by lazy {
        RemoteViews(AppUtils.getAppPackageName(), R.layout.notification_download_progress)
    }

    //创建通知notification
    private val notification by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                mNotificationId,
                mName,
                NotificationManager.IMPORTANCE_LOW
            )
            mNotificationManager.createNotificationChannel(mChannel)
            Notification.Builder(mContext)
                .setChannelId(mNotificationId)
                .setCustomContentView(mDownloadProgressView)
                .setSmallIcon(R.mipmap.ic_back_black)
                .build()
        } else {
            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.mipmap.ic_back_black)
                    .setChannelId(mNotificationId)
                    .setCustomContentView(mDownloadProgressView)
            notificationBuilder.build()
        }
    }

    //发送下载中的通知
    private fun showNotification(progress: Int) {
        if (abs(progress - mOldProgress) <= 0) {
            return
        }
        mOldProgress = progress
        mDownloadProgressView.setProgressBar(R.id.progress, 100, progress, false)
        mDownloadProgressView.setTextViewText(R.id.tv_progress, "正在下载$progress / 100")
        notification.flags = Notification.FLAG_AUTO_CANCEL
        mNotificationManager.notify("pushMessage", mMessageId, notification)
    }
}