package com.project.tvapp.fragment.rows1

import android.os.Bundle
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.project.tvapp.fragment.rows.TitlePresenter
import com.project.tvapp.fragment.rows1.presenter.StringHeaderPresenter
import com.project.tvapp.fragment.rows1.presenter.StringPresenter

/**
 * RowsSupportFragment学习页面
 * @author 张一凡
 */
class MyRowsSupportFragment1 : RowsSupportFragment() {
    //创建一个StringHeaderPresenter,用于显示每一个item的header数据
    private val mStringHeaderPresenter by lazy {
        StringHeaderPresenter()
    }

    //创建一个Presenter,这个Presenter用于显示每一个item的数据
    private val mStringPresenter by lazy {
        StringPresenter().apply {
            this.headerPresenter = mStringHeaderPresenter
        }
    }

    //创建一个adapter,用于保存数据
    private val mPageAdapter by lazy {
        ArrayObjectAdapter(mStringPresenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //添加数据
        for (i in 0 until 10) {
            val row = Row()
            mPageAdapter.add(row)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = mPageAdapter
    }
}