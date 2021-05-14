package com.project.tvapp.fragment.browse

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.hopechart.baselib.utils.Logs
import com.project.tvapp.R

class IconTitlePresenter : RowHeaderPresenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val context = parent!!.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_title_my_browse_support_fragment, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        //super.onBindViewHolder(viewHolder, item)
        Logs.e("item is $item")
        val headerItem = (item as ListRow).headerItem
        val rootView = viewHolder!!.view

        //设置图标
        rootView.findViewById<ImageView>(R.id.iv_icon).apply {
            this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.lb_action_bg))
        }

        //设置一级标题
        rootView.findViewById<TextView>(R.id.tv_first_title).apply {
            this.text = headerItem.name
        }

        //设置二级标题
        rootView.findViewById<TextView>(R.id.tv_second_title).apply {
            this.text = headerItem.description
        }
    }
}