package com.project.tvapp.fragment.rows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.project.tvapp.R

class TitlePresenter : RowHeaderPresenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {
        val context = parent?.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_title_my_rows_support_fragment, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        val holder = viewHolder!! as ViewHolder
        holder.mLeftTitle.text = if(item is ListRow) item.contentDescription else item.toString()
        holder.mRightTitle.text = "右边的标题"

    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder?) {

    }


    class ViewHolder(view: View) : RowHeaderPresenter.ViewHolder(view) {
        val mLeftTitle: TextView by lazy {
            view.findViewById<TextView>(R.id.left_title)
        }

        val mRightTitle: TextView by lazy {
            view.findViewById<TextView>(R.id.right_title)
        }
    }
}