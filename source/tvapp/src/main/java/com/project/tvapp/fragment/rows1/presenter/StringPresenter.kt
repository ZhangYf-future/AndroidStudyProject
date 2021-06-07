package com.project.tvapp.fragment.rows1.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.RowPresenter
import com.project.tvapp.R

class StringPresenter : RowPresenter() {

    class ViewHolder(view: View) : RowPresenter.ViewHolder(view) {
        val textView: TextView by lazy {
            view.findViewById<TextView>(R.id.tv_string)
        }
    }

    override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder?, item: Any?) {
        super.onBindRowViewHolder(vh, item)
        val holder = vh!! as ViewHolder
        holder.textView.text = item.toString()
    }


    override fun createRowViewHolder(parent: ViewGroup?): RowPresenter.ViewHolder {
        val context = parent?.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_string_presenter, parent, false)
        )
    }

    override fun isClippingChildren(): Boolean = true
}