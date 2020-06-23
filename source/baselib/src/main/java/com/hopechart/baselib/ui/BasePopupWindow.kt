package com.hopechart.baselib.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *@time 2020/5/11
 *@user 张一凡
 *@description popupWindow基类
 *@introduction
 */
open class BasePopupWindow<T : ViewDataBinding>(protected val context: Context) :
    PopupWindow.OnDismissListener {

    //view
    protected lateinit var mBinding: T

    private val mPopupWindow: PopupWindow by lazy {
        val result = PopupWindow(mBinding.root,setWidth(),setHeight())
        result.setOnDismissListener(this)
        result
    }

    //设置layout
    open fun setLayout(layoutId: Int,parent: ViewGroup?){
        mBinding = DataBindingUtil.inflate<T>(LayoutInflater.from(context),layoutId,parent,false)
    }


    //展示PopupWindow
    fun showPop(view: View,dx: Int, dy: Int){
        mPopupWindow.showAsDropDown(view,dx,dy)
    }

    //隐藏popupWindow
    fun dismiss(){
        mPopupWindow.dismiss()
    }

    //设置是否可以点击外部取消
    open fun isDismissClickOutside(canDismiss: Boolean){
        mPopupWindow.isOutsideTouchable = canDismiss
    }

    //设置宽度和高度
    open fun setWidth(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun setHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    override fun onDismiss() {

    }

}