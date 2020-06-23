package com.hopechart.baselib.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hopechart.baselib.BR
import com.hopechart.baselib.mvvm.BaseViewModel
import com.hopechart.baselib.mvvm.State
import com.hopechart.baselib.utils.ImmersionBarUtils
import com.hopechart.baselib.widget.LoadingDialog

/**
 *@time 2020/5/29
 *@user 张一凡
 *@description 所有的Fragment基于此类
 *@introduction
 */
abstract class BaseFragmentNew<T : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    View.OnClickListener {

    //dataBinding对象
    protected lateinit var mBinding: T

    //日志相关
    lateinit var TAG: String

    //等待框
    open var mLoadingDialog: LoadingDialog? = null

    //ViewModel对象
    protected val mViewModel: VM by lazy {
        initViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTAG(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.setVariable(BR.doClick, this)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setBarColor()
        //绑定mViewModel中有关当前状态的数据
        mViewModel.currentState.observe(viewLifecycleOwner, Observer {
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
                //网络问题
                State.STATE_REQUEST_NET_ERROR -> requestNetError()
                //其它的状态在下面的方法中处理
                else -> onViewModelState(it)
            }
        })
        initUI()
        initData()
    }


    //布局文件ID
    abstract fun getLayoutId(): Int

    //设置标题栏颜色
    open fun setBarColor() {
        //默认情况下Fragment不可以操作通知栏
    }

    //初始化UI
    abstract fun initUI()

    //初始化数据
    abstract fun initData()

    //初始化ViewModel
    abstract fun initViewModel(): VM


    open fun onViewModelState(state: Int) {
    }


    override fun onClick(v: View?) {
        v?.let {
            doClick(it)
        }
    }

    //重写此方法以设置点击事件
    open fun doClick(view: View) {

    }

    open fun initTAG(fragment: Fragment) {
        TAG = fragment.javaClass.simpleName
    }


    //弹出加载框
    open fun showLoading(message: String? = null) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(requireContext())
        }
        mLoadingDialog?.let {
            if (!it.isShowing) {
                message?.let { m -> it.setTitleText(m) }
                it.show()
            }
        }
    }

    //隐藏加载框
    open fun hiddenLoading() {
        mLoadingDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }


    //获取颜色
    protected fun getColor(colorRes: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireContext().resources.getColor(colorRes, null)
        } else {
            requireContext().resources.getColor(colorRes)
        }
    }

    open fun requestStart(){
        showLoading()
    }

    open fun requestComplete(){
        hiddenLoading()
    }

    //请求出错
    protected open fun requestError() {
        hiddenLoading()
    }

    //数据请求为空，默认隐藏等待框
    protected open fun requestEmpty(){
        hiddenLoading()
    }

    //网络问题，默认隐藏等待框
    protected open fun requestNetError(){
        hiddenLoading()
    }
}