package com.project.tvapp.fragment.browse

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.project.tvapp.R

class MediaTitlePresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_content_media_my_browse_support_fragment, null)
        return Presenter.ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        viewHolder?.view!!.findViewById<TextView>(R.id.tv_content).apply {
            this.text = item.toString()
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}