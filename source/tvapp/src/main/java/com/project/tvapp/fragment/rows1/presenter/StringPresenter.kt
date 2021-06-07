package com.project.tvapp.fragment.rows1.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.RowPresenter
import com.hopechart.baselib.utils.Logs
import com.project.tvapp.R

/**
 * 这个Presenter用于RowsSupportFragment,所以继承自RowPresenter
 */
class StringPresenter : RowPresenter() {

    private val TAG = StringPresenter::class.java.simpleName

    /**
     * item的ViewHolder
     */
    class ViewHolder(view: View) : RowPresenter.ViewHolder(view) {
        val textView: TextView by lazy {
            view.findViewById<TextView>(R.id.tv_string)
        }
    }

    /**
     * 绑定到ViewHolder
     */
    override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder?, item: Any?) {
        super.onBindRowViewHolder(vh, item)
        Log.d(TAG, "ViewHolder is $vh,item is $item")
        val holder = vh!! as ViewHolder
        holder.textView.text = item.toString()
    }


    /**
     * 创建ViewHolder
     */
    override fun createRowViewHolder(parent: ViewGroup?): RowPresenter.ViewHolder {
        val context = parent?.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_string_presenter, parent, false)
        )
    }
}