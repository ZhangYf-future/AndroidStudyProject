package com.project.tvapp.fragment.rows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowPresenter
import com.project.tvapp.R

class TitleStringPresenter : Presenter() {


    class ViewHolder(view: View) : RowPresenter.ViewHolder(view) {
        val titleTextView by lazy {
            view.findViewById<TextView>(R.id.tv_title)
        }

    }
    /*
    override fun createRowViewHolder(parent: ViewGroup?): RowPresenter.ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_title_presenter_my_rows_support_fragment, null, false)
        return ViewHolder(view)
    }

    override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder?, item: Any?) {
        super.onBindRowViewHolder(vh, item)
        val holder = vh!! as ViewHolder
        holder.titleTextView.text = item.toString()
    }
     */

    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_title_presenter_my_rows_support_fragment, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        val holder = viewHolder!! as ViewHolder
        holder.titleTextView.text = item.toString()
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder?) {

    }
}