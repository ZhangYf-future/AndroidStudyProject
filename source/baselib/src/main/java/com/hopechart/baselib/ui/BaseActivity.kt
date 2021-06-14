package com.hopechart.baselib.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.hopechart.baselib.R

abstract class BaseActivity<D : ViewDataBinding> : FragmentActivity() {

    protected open lateinit var mBinding: D

    protected open val TAG by lazy {
        this.localClassName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        if (!getDataPre()) {
            getString(R.string.error_lack_page_param)
            finish()
            return
        }
        setBarColor()
        initUI()
        initData()
    }

    //获取布局文件
    abstract fun getLayoutId(): Int

    /**
     * 获取上个页面传递的数据
     * @return true 数据获取成功  false 数据获取失败
     */
    protected open fun getDataPre(): Boolean = true

    //设置状态栏，默认空实现
    protected open fun setBarColor() {

    }

    //设置UI 默认空实现
    protected open fun initUI() {

    }

    //设置数据 默认空实现
    protected open fun initData() {
    }

    //弹出提示信息
    protected open fun showInfo(message: String) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT)
        }
    }

    open fun doClick(view: View) {
        if (view.id == R.id.iv_back)
            finish()
    }
}