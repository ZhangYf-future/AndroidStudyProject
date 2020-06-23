package com.hopechart.baselib.ui

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.hopechart.baselib.utils.Logs
import kotlin.reflect.KProperty0

/**
 *@time 2020/5/8
 *@user 张一凡
 *@description 一个默认实现的adapter
 *@introduction 用于简单的列表adapter封装
 */
class MyRViewAdapter<T>(
    context: Context,
    private val res: Int,
    private val onClick: ((t: T, position: Int) -> Unit)? = null
) :
    BaseRViewAdapter<T, BaseViewHolder<T>>(context) {

    init {
        showEmptyView = false
    }

    constructor(context: Context,res:Int,onClick:((t: T, position: Int) -> Unit)? = null,showEmpty: Boolean = false):this(context,res, onClick){
        showEmptyView = showEmpty
    }

    override fun getItemViewLayout(viewType: Int): Int = res

    override fun holderInstance(binding: ViewDataBinding): BaseViewHolder<T> {
        return object : BaseViewHolder<T>(binding) {
            override fun doClick(view: View) {
                super.doClick(view)
                val item = getItem(mPosition)
                if (item != null) {
                    onClick?.invoke(item, mPosition)
                }
            }

            override fun bind(item: T) {
                super.bind(item)

            }
        }
    }



}