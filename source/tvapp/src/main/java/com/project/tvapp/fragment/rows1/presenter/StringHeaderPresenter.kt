package com.project.tvapp.fragment.rows1.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.project.tvapp.R

class StringHeaderPresenter : RowHeaderPresenter() {

    class ViewHolder(val rootView: View) : RowHeaderPresenter.ViewHolder(rootView) {
        val textView: TextView by lazy {
            view.findViewById<TextView>(R.id.tv_string_header)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {
        Log.e("zyf", "onCreateViewHolder")
        val context = parent?.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_string_header_presenter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        super.onBindViewHolder(viewHolder, item)
        Log.e(
            "zyf",
            "viewHolder: " + viewHolder + " is " + (viewHolder is ViewHolder) + " item is " + item
        )
        if (isNullItemVisibilityGone && item == null) {
            (viewHolder as ViewHolder).rootView.visibility = View.GONE
        }

        if (viewHolder != null && viewHolder is ViewHolder) {
            viewHolder.textView.text = item.toString()
        }
    }


    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder?) {
        //super.onUnbindViewHolder(viewHolder)
    }

}