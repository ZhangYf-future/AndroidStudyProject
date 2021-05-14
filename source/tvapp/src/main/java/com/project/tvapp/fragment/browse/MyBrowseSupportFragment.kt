package com.project.tvapp.fragment.browse

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.hopechart.baselib.utils.Logs

//共有四行
private const val NUM_ROWS = 4

class MyBrowseSupportFragment : BrowseSupportFragment() {

    private val TAG by lazy {
        this.javaClass.simpleName
    }

    //显示标题的Presenter
    private val mTitlePresenter by lazy {
        IconTitlePresenter()
    }


    //显示媒体列表的adapter
    private val mMediaAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter())
    }

    //页面的PresenterSelector
    private val mPresenterSelector by lazy {
        object : PresenterSelector() {
            override fun getPresenter(item: Any?): Presenter {
                Logs.e("getPresenter item is $item")

                return mTitlePresenter
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logs.e(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logs.e(TAG, "onCreate")
        buildMediaData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logs.e(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logs.e(TAG, "onViewCreated")
        //badgeDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_loading_rotate)
        //title = "学习BrowseSupportFragment"
        //headersState = HEADERS_ENABLED
        //isHeadersTransitionOnBackEnabled = true
        //brandColor = Color.BLACK
        //searchAffordanceColor = Color.BLUE

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logs.e(TAG, "onActivityCreated")
        setHeaderPresenterSelector(mPresenterSelector)
        this.adapter = mMediaAdapter
    }

    override fun onStart() {
        super.onStart()
        Logs.e(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Logs.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Logs.e(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Logs.e(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logs.e(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logs.e(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Logs.e(TAG, "onDetach")
    }

    //构建媒体列表数据
    private fun buildMediaData() {
        for (i in 0 until 4) {
            val listRowAdapter = ArrayObjectAdapter(MediaTitlePresenter()).apply {
                add("Media Item 1")
                add("Media Item 2")
                add("Media Item 3")
            }

            HeaderItem(i.toLong(), "类别$i").apply {
                mMediaAdapter.add(ListRow(this, listRowAdapter))
            }
        }

    }

}