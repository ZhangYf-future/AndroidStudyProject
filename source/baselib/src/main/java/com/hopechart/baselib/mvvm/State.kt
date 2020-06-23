package com.hopechart.baselib.mvvm

/**
 *@time 2020/5/27
 *@user 张一凡
 *@description 状态类
 *@introduction 在ViewModel中通过这个状态类向View中传递消息
 */
class State {
    companion object {
        const val STATE_NORMAL = 0X00 //默认状态，不做任何操作
        const val STATE_SHOW_LOADING = 0X01 //显示等待框
        const val STATE_HIDDEN_LOADING = 0X002 //隐藏等待框
        const val STATE_REQUEST_START = 0X003 //开始数据请求
        const val STATE_REQUEST_COMPLETE = 0X004 //数据请求完成
        const val STATE_REQUEST_ERROR = 0X005 //数据请求出错
        const val STATE_REQUEST_DATA_EMPTY = 0x006 //数据请求为空
        const val STATE_REQUEST_NET_ERROR = 0x007 //网络出现问题
        const val STATE_LOGIN_SUCCESS = 0x0078 //登录成功
    }
}