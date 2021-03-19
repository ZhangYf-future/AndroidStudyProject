package com.project.mystudyproject.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.bumptech.glide.RequestBuilder
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityOkHttpBasicUsageBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.*

/**
 * @作者 zyf
 * @日期 2021/3/15
 * @说明 OkHttp基本用法页面
 * @修改信息
 */
class OkHttpBasicUsageActivity : BaseActivity<ActivityOkHttpBasicUsageBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_ok_http_basic_usage

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_send_get_request -> {
                sendGetQuest()
            }

            R.id.btn_send_post_request ->
                sendPostRequest()
        }

    }

    //发送一个get请求
    private fun sendGetQuest() {
        val address = "http://www.baidu.com"
        val client = OkHttpClient()

        //创建请求信息
        val request = Request.Builder().let {
            it.url(address)
            it.build()
        }
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logs.e("请求出错$e}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Logs.e("请求完成：${response.body!!.string()}")
                }

            })
    }

    //发送一个post请求
    private fun sendPostRequest() {
        //请求地址
        val address = "http://202.96.122.219:50280/api/login"
        //创建请求体
//        val requestBody =  FormBody.Builder().let {
//            it.add("username","test")
//            it.add("password","123456")
//            it.build()
//        }
        val requestBody = MultipartBody.Builder().let {
            it.addFormDataPart("username", "test")
            it.addFormDataPart("password", "123456")
            it.setType("multipart/form-data".toMediaType())
            it.build()
        }

        Logs.e("请求体的类型:${requestBody.type}")
        //创建请求信息
        val request = Request.Builder().let {
            it.url(address)
            it.post(requestBody)
            it.build()
        }
        //创建客户端
        val builder = OkHttpClient.Builder()
        val eventListener = object : EventListener() {
            override fun callStart(call: Call) {
                Logs.e("callStart:$call")
                super.callStart(call)
            }
        }
        val executor =
            ThreadPoolExecutor(2, 3, 60, TimeUnit.SECONDS, ArrayBlockingQueue<Runnable>(1),
                ThreadFactory {

                    val t = Thread(it)
                    Logs.e("创建线程$t")

                    t.name = "自己创建的线程"
                    t
                })
        //val executor = Executors.newSingleThreadExecutor()
        val dispatcher = Dispatcher(executor)
        dispatcher.maxRequests = 3
        dispatcher.maxRequestsPerHost = 3
        dispatcher.idleCallback = Runnable {
            Logs.e("请求结束后执行")
        }

        builder.eventListenerFactory(object : EventListener.Factory {
            override fun create(call: Call): EventListener {
                return eventListener
            }
        })

        builder.addInterceptor {
            Logs.e("interceptor 等待中:${Thread.currentThread().name}")
            Thread.sleep(2 * 1000)
            it.proceed(it.request())
        }

        builder.addNetworkInterceptor {
            Logs.e("networkInterceptor")
            it.proceed(it.request())
        }

        builder.dispatcher(dispatcher)
        builder.callTimeout(60 * 1000L, TimeUnit.SECONDS)
        val client = builder.build()

        val call = client.newCall(request = request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Logs.e("请求出错1:$e")
            }

            override fun onResponse(call: Call, response: Response) {
                Logs.e("请求完成1:$call")
            }
        })

        val call1 = client.newCall(request = request)
        call1.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Logs.e("请求出错2:$e")
            }

            override fun onResponse(call: Call, response: Response) {
                Logs.e("请求完成2:$call")
            }
        })

        val call2 = client.newCall(request = request)
        call2.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Logs.e("请求出错3:$e")
            }

            override fun onResponse(call: Call, response: Response) {
                Logs.e("请求完成3:$call")
            }
        })

        val call3 = client.newCall(request = request)
        call3.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Logs.e("请求出错4:$e")
            }

            override fun onResponse(call: Call, response: Response) {
                Logs.e("请求完成4:$call")
            }
        })

        val call4 = client.newCall(request = request)
        call4.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Logs.e("请求出错5:$e")
            }

            override fun onResponse(call: Call, response: Response) {
                Logs.e("请求完成5:$call")

            }
        })
    }


}

fun View.toast() = Toast.makeText(this.context, "测试", Toast.LENGTH_SHORT)
