package com.hopechart.baselib.mvvm


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hopechart.baselib.R
import com.hopechart.baselib.utils.ResourceUtil
import com.hopechart.baselib.utils.ToastUtils

/**
 *@time 2020/5/27
 *@user 张一凡
 *@description ViewModel基类
 *@introduction 注意ViewModel主要操作和逻辑相关的部分
 */
open class BaseViewModel : ViewModel() {

    //向页面传递当前需要执行的操作
    val currentState: MutableLiveData<Int> = MutableLiveData<Int>()


    //显示错误信息
    open fun showInfo(message: String) {
        ToastUtils.showShort(message)
    }

    //显示错误信息
    open fun showInfo(res: Int) {
        ToastUtils.showShort(res)
    }

    //显示等待弹框
    open fun showLoading() {
        currentState.postValue(State.STATE_SHOW_LOADING)
    }

    //隐藏等待框
    open fun hiddenLoading() {
        currentState.postValue(State.STATE_HIDDEN_LOADING)
    }

    //获取字符串
    protected fun getString(resource: Int): String = ResourceUtil.getString(resource)


    //开始请求
    fun requestStart() {
        currentState.postValue(State.STATE_REQUEST_START)
    }

    //数据请求完成
    fun requestComplete() {
        //向页面发送数据请求完成的状态
        currentState.postValue(State.STATE_REQUEST_COMPLETE)
    }

    //数据请求出错的提示
    fun requestError(message: String? = null) {
        //向页面发送请求出错的状态
        currentState.postValue(State.STATE_REQUEST_ERROR)
        //如果提示的信息不为空，则弹出提示信息
        showInfo(message ?: getString(R.string.error_request))
    }

    //数据请求为空的提示
    open fun requestEmpty(message: String? = null) {
        //向页面发送数据为空的状态
        currentState.postValue(State.STATE_REQUEST_DATA_EMPTY)
        //如果提示的信息不为空，则弹出提示信息
        showInfo(message ?: getString(R.string.data_empty))
    }

    //网络出错的提示
    open fun requestNetError(message: String? = null) {
        //向页面中发送网络出错的状态
        currentState.postValue(State.STATE_REQUEST_NET_ERROR)
        showInfo(message ?: getString(R.string.error_net))
    }


}