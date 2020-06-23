package com.hopechart.baselib.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hopechart.baselib.data.PageEntity
import com.hopechart.baselib.mvvm.BaseViewModel
import com.hopechart.baselib.utils.ImmersionBarUtils
import com.hopechart.baselib.widget.MyXRecyclerView
import com.jcodecraeer.xrecyclerview.XRecyclerView

/**
 *@time 2020/6/3
 *@user 张一凡
 *@description 列表数据页面
 *@introduction
 */
abstract class BaseXRecyclerViewActivityNew<T : ViewDataBinding, VM : BaseViewModel, D> :
    BaseActivityNew<T, VM>(), XRecyclerView.LoadingListener {
    //默认创建的adapter
    protected val mAdapter: BaseRViewAdapter<D, BaseViewHolder<D>> by lazy {
        initAdapter()
    }

    //列表recyclerView
    private val mRecyclerView: MyXRecyclerView by lazy {
        getRecyclerView()
    }

    //默认不允许加载更多
    private var canLoadMore: Boolean = false

    //数据加载页面
    protected var mLoadPage: Int = 1

    //每页加载的数据量
    protected var mPages: Int = 10

    //加载类型,默认是刷新
    protected open var mIsRefresh: Boolean = true

    //总数量，当后台传递的列表在row中时会使用
    protected var mTotal: Int = 0

    //初始化recyclerView
    abstract fun getRecyclerView(): MyXRecyclerView

    //初始化adapter
    abstract fun initAdapter(): BaseRViewAdapter<D, BaseViewHolder<D>>

    //请求数据
    abstract fun loadData()

    //列表排列样式
    protected open fun initLayoutManger(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    //基于当前View调整状态栏
    abstract fun getBarView(): View?

    //调整状态栏
    override fun setBarColor() {
        //默认基于屏幕顶部布局
        if (getBarView() != null) {
            ImmersionBarUtils.transBarWithHeight(this, getBarView()!!)
        } else {
            super.setBarColor()
        }
    }


    override fun initUI() {
        //默认允许刷新操作
        mRecyclerView.setPullRefreshEnabled(true)
        //默认禁止加载更多的操作
        mRecyclerView.setLoadingMoreEnabled(canLoadMore)
        //加载数据的接口
        mRecyclerView.setLoadingListener(this)
        //列表布局方式
        mRecyclerView.layoutManager = initLayoutManger()
        //列表adapter
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        mRecyclerView.refresh()
    }


    override fun onLoadMore() {
        mLoadPage++
        mIsRefresh = false
        loadData()
    }


    override fun onRefresh() {
        mLoadPage = 1
        mIsRefresh = true
        mRecyclerView.setLoadingMoreEnabled(canLoadMore)
        loadData()
    }

    //显示列表数据
    protected fun showData(data: PageEntity<D>) {
        if (mIsRefresh) {
            mAdapter.clear()
            mAdapter.setData(data.list)
            mRecyclerView.refreshComplete()
            mRecyclerView.setLoadingMoreEnabled(data.isHasNextPage)
        } else {
            mAdapter.insert(mAdapter.getItemSize(), data.list)
            mRecyclerView.loadMoreComplete()
        }
        mRecyclerView.setNoMore(!data.isHasNextPage)
    }

    protected fun showRowData(data: PageEntity<D>) {
        if (mIsRefresh) {
            mAdapter.clear()
            mAdapter.setData(data.rows)
            mRecyclerView.refreshComplete()
            mRecyclerView.setLoadingMoreEnabled(data.isHasNextPage)
        } else {
            mAdapter.insert(mAdapter.getItemSize(), data.rows)
            mRecyclerView.loadMoreComplete()
        }
        mRecyclerView.setNoMore(!data.isHasNextPage)
    }

    //显示列表数据
    protected fun showData(data: List<D>) {
        var isHasNextPage: Boolean = false
        if (mIsRefresh) {
            mAdapter.clear()
            mAdapter.setData(data)
            mRecyclerView.refreshComplete()
            isHasNextPage = mAdapter.getItemSize() < mTotal
            mRecyclerView.setLoadingMoreEnabled(isHasNextPage)
        } else {
            mAdapter.insert(mAdapter.getItemSize(), data)
            isHasNextPage = mAdapter.getItemSize() < mTotal
            mRecyclerView.loadMoreComplete()
        }
        mRecyclerView.setNoMore(!isHasNextPage)
    }

    override fun requestError() {
        super.requestError()
        mRecyclerView.refreshComplete()
        mRecyclerView.loadMoreComplete()
    }

    override fun showLoading() {
        //由于XRecyclerView已经有加载的动画了，这里不再显示加载框
    }

    override fun hiddenLoading() {
        super.hiddenLoading()
        mRecyclerView.refreshComplete()
        mRecyclerView.loadMoreComplete()
    }

    override fun onDestroy() {
        mRecyclerView.destroy()
        super.onDestroy()
    }
}