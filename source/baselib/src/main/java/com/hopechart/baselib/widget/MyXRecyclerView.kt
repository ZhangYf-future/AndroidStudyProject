package com.hopechart.baselib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.hopechart.baselib.utils.DensityUtils
import com.hopechart.baselib.utils.Logs
import com.jcodecraeer.xrecyclerview.LoadingMoreFooter
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
class MyXRecyclerView : XRecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        defaultFootView?.setPadding(0, DensityUtils.dp2px(8f), 0, DensityUtils.dp2px(8f))
        setFootViewText("正在加载...", "已经到底了")

        //全局设置默认显示的刷新和加载更多样式
        setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple)
    }

    override fun setNoMore(noMore: Boolean) {
        super.setNoMore(noMore)
        if (noMore) {
            defaultFootView.visibility = View.VISIBLE
        }
    }
}