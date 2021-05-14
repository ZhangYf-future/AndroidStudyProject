package com.project.tvapp.fragment.brabded

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.leanback.widget.SearchOrbView
import androidx.leanback.widget.TitleViewAdapter
import com.project.tvapp.R
import kotlinx.android.synthetic.main.activity_branded_fragment_study.*

/**
 * BrabdedSupportFragment学习页面
 * @author 张一凡 zyfFuture@163.com
 */
class BrandedFragmentStudyActivity : AppCompatActivity() {

    private val mContentFragment by lazy {
        MyBrandedSupportFragment().apply {
            this.title = "这是BrandedFragment设置的title"
//            this.badgeDrawable = ContextCompat.getDrawable(
//                this@BrandedFragmentStudyActivity,
//                R.drawable.baselib_default_pic_circle
//            )
            this.searchAffordanceColors =
                SearchOrbView.Colors(
                    Color.parseColor("#ff0000"),
                    Color.parseColor("#00ff00"),
                    Color.parseColor("#0000ff")
                )

            this.setOnSearchClickedListener {
                Log.e("TAG", "点击搜索接口回调")
            }

            this.showTitle(TitleViewAdapter.FULL_VIEW_VISIBLE)
        }
    }

    //Fragment Tag
    private val mContentFragmentTag = "MyBrandedSupportFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branded_fragment_study)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, mContentFragment, mContentFragmentTag)
            .commit()

        btn_change_title_show.setOnClickListener {
            mContentFragment.showTitle(!mContentFragment.isShowingTitle)
        }
    }
}