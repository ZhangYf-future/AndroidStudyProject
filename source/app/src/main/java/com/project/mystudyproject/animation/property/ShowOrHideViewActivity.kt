package com.project.mystudyproject.animation.property

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityShowOrHideViewBinding

/**
 * 使用动画显示或者隐藏视图
 */
class ShowOrHideViewActivity : BaseActivity<ActivityShowOrHideViewBinding>() {

    //动画持续时间
    private var mShortAnimationDuration = 1000

    //当前显示的是否是卡片背面
    private var mShowingBack = false

    private val mFrontFragment by lazy {
        CardFrontFragment()
    }

    private val mBackFragment by lazy {
        CardBackFragment()
    }

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_show_or_hide_view

    override fun initUI() {
        super.initUI()
        //获取动画持续时间
        //mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        //默认添加卡片正面Fragment
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_content, mFrontFragment)
                .commit()
        }
    }

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_use_fade_in_fade_out_animator ->
                crossFade()
            R.id.btn_show_card_back ->
                showCardBack()
            R.id.btn_show_card_front ->
                showCardFront()
            R.id.btn_hide_view_with_circle_animator ->
                hideViewWithCircleAnimator()
            R.id.btn_show_view_with_circle_animator ->
                showViewWithCircleAnimator()
        }
    }

    //开始执行淡入淡出动画
    private fun crossFade() {
        //设置需要淡入的View的alpha为0，可见性为VISIBLE
        mBinding.tvContent.apply {
            alpha = 0f
            visibility = View.VISIBLE
            //通过动画将透明度变为1.0
            animate()
                .alpha(1.0f)
                .setDuration(mShortAnimationDuration.toLong())
                .start()
        }

        //设置需要淡出的动画，将其alpha从1变为0，并通过监听动画执行事件，在动画结束后将View的可见性设置为GONE
        mBinding.loadingProgress.animate()
            .alpha(0f)
            .setDuration(mShortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mBinding.loadingProgress.visibility = View.GONE
                }
            })
            .start()
    }


    //显示卡片背面
    private fun showCardBack() {
        if (mShowingBack) {
            //supportFragmentManager.popBackStack()
            return
        }
        mShowingBack = true
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out
            )
            replace(R.id.fl_content, mBackFragment)
            //addToBackStack(null)
            commit()
        }
    }

    //显示卡片正面
    private fun showCardFront() {
        if (!mShowingBack) {
            //supportFragmentManager.popBackStack()
            return
        }
        mShowingBack = false
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out
            )
            replace(R.id.fl_content, mFrontFragment)
            //addToBackStack(null)
            commit()
        }
    }


    //使用圆形揭露动画隐藏视图
    private fun hideViewWithCircleAnimator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = mBinding.tvView.width / 2
            val cy = mBinding.tvView.height / 2
            val anim =
                ViewAnimationUtils.createCircularReveal(mBinding.tvView, cx, cy, cx.toFloat(), 0f)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    //动画运行结束后将View设置为GONE
                    mBinding.tvView.visibility = View.GONE
                }
            })
            anim.start()
        } else {
            mBinding.tvView.visibility = View.GONE
        }
    }

    //使用圆形揭露动画显示视图
    private fun showViewWithCircleAnimator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = mBinding.tvView.width / 2
            val cy = mBinding.tvView.height / 2
            val anim =
                ViewAnimationUtils.createCircularReveal(mBinding.tvView, cx, cy, 0f, cx.toFloat())
            mBinding.tvView.visibility = View.VISIBLE
            anim.start()
        } else {
            mBinding.tvView.visibility = View.VISIBLE
        }
    }

    //卡片切换的Fragment
    class CardFrontFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_card_front, container, false)
        }
    }

    class CardBackFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_card_back, container, false)
        }
    }
}