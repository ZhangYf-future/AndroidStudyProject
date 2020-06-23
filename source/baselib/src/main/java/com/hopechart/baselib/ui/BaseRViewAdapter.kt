package com.hopechart.baselib.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hopechart.baselib.R
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.widget.MyXRecyclerView
import com.jcodecraeer.xrecyclerview.XRecyclerView

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
abstract class BaseRViewAdapter<T, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<BaseViewHolder<T>> {

    companion object {
        const val EMPTY_VIEW_TYPE: Int = -1000
    }

    protected val context: Context

    //数据总数
    protected var total: Int? = null

    //更新时可实现局部动画，可不传
    private val recyclerView: XRecyclerView?

    //数据列表
    private var items = mutableListOf<T>()

    //数据为空时显示的View
    private var emptyView: View?

    //是否显示空的布局,默认不显示空的布局,如果需要显示空布局则需要在构造函数中明确指定
    protected var showEmptyView: Boolean = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, total: Int) : this(context) {
        this.total = total
    }
    constructor(
        context: Context,
        emptyView: View?
    ) : this(context, null, emptyView)

    constructor(context: Context,showEmptyView: Boolean):this(context){
        this.showEmptyView = showEmptyView
    }

    constructor(
        context: Context,
        recyclerView: XRecyclerView?
    ) : this(context, recyclerView, null)

    constructor(
        context: Context,
        recyclerView: XRecyclerView?,
        emptyView: View?
    ) {
        this.context = context
        this.recyclerView = recyclerView
        this.emptyView = emptyView
        if (this.emptyView == null) {
            this.emptyView =
                LayoutInflater.from(context).inflate(R.layout.view_no_data, null, false)
        }
        initDataObserver()
    }

    //获取布局ID
    abstract fun getItemViewLayout(viewType: Int): Int

    //实例化ViewHolder
    abstract fun holderInstance(binding: ViewDataBinding): VH

    //绑定ViewHolder
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (holder.itemView !is AdapterView<*>) {
            holder.itemView.setOnClickListener {
                it?.let {
                    holder.doClick(it)
                }
            }
        }
        holder.mPosition = position
        total?.let {
            holder.mTotal = it
        }
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        if (viewType == EMPTY_VIEW_TYPE) {
            //如果是空的布局，就构造一个空的布局
            if (emptyView != null) {
                val binding = DataBindingUtil.bind<ViewDataBinding>(emptyView!!)
                if (binding != null) {
                    return object : BaseViewHolder<T>(binding) {}
                }
            }
        } else {
            //正常构造一个布局
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(context),
                getItemViewLayout(getItemViewType(viewType)),
                parent,
                false
            )
            return holderInstance(binding)
        }
        //默认返回一个空的布局
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            R.layout.base_layout_item, parent, false
        )
        return object : BaseViewHolder<T>(binding) {}
    }

    //返回列表总数，注意这里会考虑到列表为空时显示的数据
    override fun getItemCount(): Int = if (isShowEmptyView()) 1 else items.size

    //返回列表项item,注意这里会考虑到列表项为空的情况
    override fun getItemViewType(position: Int): Int =
        if (isShowEmptyView()) EMPTY_VIEW_TYPE else super.getItemViewType(position)


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView is MyXRecyclerView) {
            //在MyXRecyclerView中已经完成处理
            return
        }
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val gridManager: GridLayoutManager? = manager
            gridManager?.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isShowEmptyView()) gridManager.spanCount else 1
                }
            })
        }
    }

    //刷新列表时需要调用这个方法
    open fun xrIsEmpty(): Boolean {
        if (recyclerView == null) {
            notifyDataSetChanged()
            return true
        }
        return false
    }

    //获取准确的列表中的数据
    fun getItemSize(): Int = items.size


    //判断是否显示空的View
    private fun initDataObserver() {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                if (showEmptyView) {
                    if (items.isEmpty() && emptyView != null) {
                        showEmptyView = true
                        notifyItemRangeChanged(0, itemCount)
                    } else {
                        showEmptyView = false
                    }
                }
            }
        })
    }

    //是否显示空的布局
    private fun isShowEmptyView(): Boolean = showEmptyView

    //获取指定position的数据
    open fun getItem(position: Int): T? =
        if (position >= 0 && position < items.size) items[position] else null

    fun getRecyclerView(): XRecyclerView? = recyclerView

    //设置数据
    fun setData(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open fun insert(item: T) {
        items.add(itemCount, item)
        if (!xrIsEmpty()) {
            getRecyclerView()?.notifyItemInserted(items, itemCount)
        }
    }

    open fun insert(items: List<T>?) {
        this.items.addAll(itemCount, items!!)
        if (!xrIsEmpty()) {
            getRecyclerView()?.notifyItemInserted(items, itemCount)
        }
    }

    open fun insert(position: Int, item: T) {
        items.add(position, item)
        if (!xrIsEmpty()) {
            getRecyclerView()?.notifyItemInserted(items, position)
        }
    }

    open fun insert(position: Int, items: List<T>?) {
        this.items.addAll(position, items!!)
//        if (!xrIsEmpty()) {
//            getRecyclerView()?.notifyItemInserted(items, position)
//        }
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        if (position > -1 && position < itemCount) {
            val remove: T? = items.removeAt(position)
            if (remove != null) {
                if (!xrIsEmpty()) {
                    getRecyclerView()?.notifyItemRemoved(items, position)
                }
            }
        }
    }

    open fun remove(item: T) {
        var i = 0
        while (i < items.size) {
            val temp = items[i]
            if (temp == item) {
                remove(i)
                i--
            }
            i++
        }
    }

    open fun notifyPosition(position: Int) {
        if (!xrIsEmpty()) {
            getRecyclerView()?.let {
                it.notifyItemChanged(position)
            }
            recyclerView?.notifyItemChanged(position)
        }
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    open fun getItems(): List<T>? {
        return items
    }

    //设置数量
    fun setTotal(total: Int) {
        this.total = total
    }

}