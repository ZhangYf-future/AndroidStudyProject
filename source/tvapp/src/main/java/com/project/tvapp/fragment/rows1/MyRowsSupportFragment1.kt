package com.project.tvapp.fragment.rows1

import android.os.Bundle
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter

/**
 * RowsSupportFragment学习页面
 * @author 张一凡
 */
class MyRowsSupportFragment1 : RowsSupportFragment() {
    //创建一个adapter,用于设置数据
    private val mPageAdapter by lazy {
        ArrayObjectAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //添加数据
        for (i in 0 until 10) {
            mPageAdapter.add(i.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = mPageAdapter
    }
}