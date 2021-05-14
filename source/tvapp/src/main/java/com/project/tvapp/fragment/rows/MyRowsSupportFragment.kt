package com.project.tvapp.fragment.rows

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.project.tvapp.fragment.rows.data.MediaDataEntity

/**
 * 学习RowsSupportFragment的使用
 */
class MyRowsSupportFragment : RowsSupportFragment() {

    //创建一个PresenterSelector
    private val mPresenterSelector by lazy {
        object : PresenterSelector() {
            override fun getPresenter(item: Any?): Presenter = when (item) {
                is MediaDataEntity -> mMediaDataPresenter

                else -> mStringPresenter
            }
        }
    }

    //创建一个Presenter
    private val mStringPresenter by lazy {
        TitleStringPresenter()
    }

    //创建和MediaData相关的Presenter
    private val mMediaDataPresenter by lazy {
        TitleMediaDataPresenter()
    }

    //显示标题的Presenter
    private val mTitlePresenter by lazy {
        TitlePresenter()
    }

    //整个页面的adapter
    private val mTitleAdapter by lazy {
        ArrayObjectAdapter(mPagePresenter)
    }

    //整个页面的Presenter
    private val mPagePresenter by lazy {
        ListRowPresenter().apply {
            this.headerPresenter = mTitlePresenter
        }
    }

    /**
     * 在onCreate()中添加数据
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //添加数据
        for (i in 0 until 10) {
            val adapter = ArrayObjectAdapter(mPresenterSelector)
            for (j in 0 until 30) {
                if (j % 2 == 0) {
                    adapter.add(j.toString())
                } else {
                    adapter.add(
                        MediaDataEntity(
                            "第$j 个数据",
                            "数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介数据简介"
                        )
                    )
                }
            }

            val listRow = if (i % 3 == 0) ListRow(HeaderItem("这是标题"), adapter) else ListRow(adapter)
            mTitleAdapter.add(listRow)
        }
    }

    /**
     * 在onActivityCreated()方法中绑定数据
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //绑定数据
        adapter = mTitleAdapter
    }
}