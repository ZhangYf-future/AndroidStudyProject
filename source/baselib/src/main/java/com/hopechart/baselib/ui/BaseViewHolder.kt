package com.hopechart.baselib.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hopechart.baselib.BR

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
abstract class BaseViewHolder<T> : RecyclerView.ViewHolder {

    var mPosition: Int = 0
    var mTotal : Int = 0
    private val mBinding: ViewDataBinding

    constructor(binding: ViewDataBinding): super (binding.root){
        this.mBinding = binding
    }

    open fun bind(item: T){
        mBinding.setVariable(BR.position,mPosition)
        mBinding.setVariable(BR.total,mTotal)
        mBinding.setVariable(getVariableId(),item)
        mBinding.setVariable(getListenerId(),object : View.OnClickListener{
            override fun onClick(v: View?) {
                v?.let {
                    doClick(it)
                }
            }
        })
        //立即刷新页面
        mBinding.executePendingBindings()
    }

    open fun doClick(view: View){

    }

    private fun getVariableId(): Int {
        return BR.item
    }

    private fun getListenerId(): Int {
        return BR.doClick
    }

    open fun getBinding(): ViewDataBinding {
        return mBinding
    }
}