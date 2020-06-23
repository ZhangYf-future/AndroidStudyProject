package com.hopechart.baselib.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.hopechart.baselib.R
import com.hopechart.baselib.mvvm.BaseViewModel
import com.hopechart.baselib.mvvm.State
import com.hopechart.baselib.utils.ImmersionBarUtils
import com.hopechart.baselib.utils.KeyboardUtils
import com.hopechart.baselib.utils.Logs
import com.hopechart.baselib.utils.ToastUtils
import com.hopechart.baselib.widget.LoadingDialog

/**
 *@time 2020/5/27
 *@user 张一凡
 *@description 所有的Activity默认基于此类
 *@introduction 默认情况下一个页面对应一个ViewModel，但是在某些特殊情况下可能出现一个页面需要依赖多个ViewModel的情况，
 * 只需要重新实例化一个VieModel即可，同时存在两个ViewModel不会出现问题
 * 另外注意不要在页面中执行任何和逻辑有关的操作，逻辑操作请全部放在ViewModel中操作
 */
abstract class BaseActivityNew<T : ViewDataBinding, VM : BaseViewModel> : FragmentActivity() {

    //当前实例
    protected lateinit var instance: FragmentActivity

    //DataBinding对象
    protected lateinit var mBinding: T

    //当前ViewModel对象
    protected val mViewModel: VM by lazy {
        initViewModel()
    }

    //日志
    protected open lateinit var TAG: String

    //加载框
    open var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置实例为此页面
        instance = this
        //页面DataBinding
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        //将DataBinding绑定当前页面的声明周期
        mBinding.lifecycleOwner = this
        //对状态栏进行操作
        setBarColor()
        //初始化页面内容部分
        initContent()
        //设置页面TAG
        initTAG(this)
        //绑定ViewModel中传递状态的字段到当前页面，根据这个状态字段显示不同的信息
        mViewModel.currentState.observe(this, Observer {
            when (it) {
                //显示等待加载框
                State.STATE_SHOW_LOADING -> showLoading()
                //隐藏加载框
                State.STATE_HIDDEN_LOADING -> hiddenLoading()
                //开始请求数据默认显示加载框
                State.STATE_REQUEST_START -> requestStart()
                //数据请求完成，隐藏加载框
                State.STATE_REQUEST_COMPLETE -> requestComplete()
                //数据请求出错，默认隐藏加载框，注意对于弹出的Toast信息已经在ViewModel中处理了，不需要再这里再处理
                State.STATE_REQUEST_ERROR -> requestError()
                //数据请求为空
                State.STATE_REQUEST_DATA_EMPTY -> requestEmpty()
                //网络异常
                State.STATE_REQUEST_NET_ERROR -> requestNetError()
                //其它的状态在下面的方法中处理
                else -> onViewModeState(it)
            }
        })
    }

    //获取并依据文件
    abstract fun getLayoutId(): Int

    //设置状态栏颜色
    open fun setBarColor() {
        //默认设置白色状态栏
        ImmersionBarUtils.whiteStatusBar(this, true)
    }

    //内容部分
    private fun initContent() {
        if (initBundle()) {
            initUI()
            initData()
        }
    }

    //初始化viewModel
    abstract fun initViewModel(): VM

    //初始化bundle，默认返回true
    open fun initBundle(): Boolean = true

    //初始化UI
    abstract fun initUI()

    //初始化数据
    abstract fun initData()

    //点击事件
    open fun doClick(view: View?) {
        view?.let {
            clickView(it)
        }
    }

    //重写此事件以响应点击事件
    protected open fun clickView(view: View) {
        if (view.id == R.id.iv_back) {
            onBackPressed()
        }
    }


    open fun initTAG(context: Context) {
        TAG = context.javaClass.simpleName
    }

    //弹出加载框
    open fun showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
        mLoadingDialog?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    //隐藏加载框
    open fun hiddenLoading() {
        mLoadingDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    override fun onBackPressed() {
        KeyboardUtils.hideKeyboard(mBinding.root)
        super.onBackPressed()
    }

    open fun showInfo(res: Int) {
        ToastUtils.showShort(res)
    }

    open fun showInfo(message: String) {
        ToastUtils.showShort(message)
    }

    //从ViewModel部分传递过来的其它事件
    protected open fun onViewModeState(state: Int) {}

    //开始请求数据
    protected open fun requestStart() {
        showLoading()
    }

    //请求完成
    protected open fun requestComplete() {
        hiddenLoading()
    }

    //请求出错
    protected open fun requestError() {
        hiddenLoading()
    }

    //数据请求为空，默认隐藏等待框
    protected open fun requestEmpty() {
        hiddenLoading()
    }

    //网络异常，默认隐藏等待框，不做其它操作
    protected open fun requestNetError() {
        hiddenLoading()
    }
}