package com.project.tvapp.fragment.rows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowPresenter
import com.project.tvapp.R
import com.project.tvapp.fragment.rows.data.MediaDataEntity

class TitleMediaDataPresenter : Presenter() {
    /*
    override fun createRowViewHolder(parent: ViewGroup?): RowPresenter.ViewHolder {
        val context = parent?.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_title_media_data, null, false)
        return ViewHolder(view)
    }

    override fun onBindRowViewHolder(vh: RowPresenter.ViewHolder?, item: Any?) {
        super.onBindRowViewHolder(vh, item)
        val holder = vh!! as ViewHolder
        if (item != null && item is MediaDataEntity) {
            holder.mTitleTextView.text = item.name
            holder.mDescriptionTextView.text = item.description
        }
    }
     */


    class ViewHolder(view: View) : RowPresenter.ViewHolder(view) {
        val mTitleTextView: TextView by lazy {
            view.findViewById<TextView>(R.id.tv_title)
        }

        val mDescriptionTextView: TextView by lazy {
            view.findViewById<TextView>(R.id.tv_description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {
        val context = parent?.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_title_media_data, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        val holder = viewHolder!! as ViewHolder
        if (item != null && item is MediaDataEntity) {
            holder.mTitleTextView.text = item.name
            holder.mDescriptionTextView.text = item.description
        }
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder?) {

    }
}