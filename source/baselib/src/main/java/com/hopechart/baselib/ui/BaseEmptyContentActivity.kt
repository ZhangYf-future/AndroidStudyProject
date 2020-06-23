package com.hopechart.baselib.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hopechart.baselib.R
import com.hopechart.baselib.mvvm.BaseViewModel
import com.hopechart.baselib.mvvm.State
import com.hopechart.baselib.utils.ImmersionBarUtils

/**
 *@date: 2020/6/10
 *@author: 张一凡
 *@description: 默认包含空数据提醒的页面基类
 *@introduction: 在这个类中将页面分为标题和内容两部分，当内容中的数据为空的时候会加载一个数据为空的提示View
 */
abstract class BaseEmptyContentActivity<T : ViewDataBinding, VM : BaseViewModel> :
    BaseActivityNew<T, VM>() {

    //默认的为空的布局
    private val mEmptyView by lazy {
        LayoutInflater.from(this).inflate(R.layout.base_layout_empty, getContent(), false)
    }

    /**
     * 基于当前View对状态栏进行调整
     * 默认情况处理状态为白底黑字的样式
     * 但是有时候需要将标题栏设置在状态栏下面，由于无法确认状态栏的高度，所以在此处传递一个View，
     * 在Utils中会根据这个View设置PaddingTop的值，从而将标题栏设置到状态栏下面而又不会让标题栏内容和状态栏重叠
     */
    abstract fun getBarRelateView(): View?

    /**
     * 页面内容部分的ViewGroup
     * 由于在页面没有数据或者网络出错的时候弹出覆盖整个页面的提示信息，所以在这里传递一个页面内容布局
     * 当需要提示数据为空或者网络出错的时候则将提示信息的View加载到这个页面的最顶部
     * 注意内容部分不要使用LineaLayout这种布局，因为使用ViewGroup.add(view)这种方式加载提示信息的View，
     * 所以使用LinearLayout这种布局会导致将提示信息的View加载到页面的最下面或最右边，最终会导致提示信息的位置不对
     */
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
        if (emptyView.parent == null) {
            getContent().addView(emptyView)
        }
    }


    //点击空布局执行的方法,默认不执行任何方法，有时候需要在点击之后重新请求数据,则可以在这个方法中将第一个View清空
    protected open fun clickEmpty() {
        if (canClickEmpty())
            removeEmptyView()
    }

    override fun setBarColor() {
        if (getBarRelateView() != null)
            ImmersionBarUtils.transBarWithHeight(this, getBarRelateView()!!)
        else
            super.setBarColor()
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