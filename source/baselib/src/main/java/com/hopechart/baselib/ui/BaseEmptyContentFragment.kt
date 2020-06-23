package com.hopechart.baselib.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hopechart.baselib.R
import com.hopechart.baselib.mvvm.BaseViewModel
import com.hopechart.baselib.mvvm.State

/**
 *@time 2020/6/10
 *@user 张一凡
 *@description
 *@introduction
 */
abstract class BaseEmptyContentFragment<T : ViewDataBinding, VM : BaseViewModel> :
    BaseFragmentNew<T, VM>() {
    //默认的为空的布局
    private val mEmptyView by lazy {
        LayoutInflater.from(requireContext()).inflate(R.layout.base_layout_empty, getContent(), false)
    }


    //获取内容部分的ViewGroup
    abstract fun getContent(): ViewGroup

    //返回一个自定义的空布局，可为空，为空时使用默认的空布局
    protected open fun getEmptyView(): View? = null

    //是否需要响应空布局的点击事件，默认不需要
    protected open fun canClickEmpty() = false


    override fun requestEmpty() {
        super.requestEmpty()
        val emptyView = getEmptyView() ?: mEmptyView
        //设置空布局的点击事件
        emptyView?.setOnClickListener {
            clickEmpty()
        }
        //将空布局view添加到第一个元素
        getContent().addView(emptyView)
    }

    //点击空布局执行的方法,默认不执行任何方法，有时候需要在点击之后重新请求数据,则可以在这个方法中将第一个View清空
    protected open fun clickEmpty() {
        if (canClickEmpty())
            removeEmptyView()
    }

    //清空添加的空View
    private fun removeEmptyView() {
        val content = getContent()
        val emptyView = content.getChildAt(content.childCount - 1)
        if (emptyView == mEmptyView || emptyView == getEmptyView()) {
            content.removeView(emptyView)
        }
    }
}