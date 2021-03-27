package com.project.mystudyproject.sp

import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySpBinding

class SpActivity : BaseActivity<ActivitySpBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_sp

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_get_and_save_package_name -> getAndSavePackageName()

        }
    }

    //存取包名
    private fun getAndSavePackageName() {
        val packageName = this.packageName
        val utils = SpUtils(SpUtils.PACKAGE_NAME)
        Logs.e("存储包名前的数据:${utils.value}")
        Logs.e("设置的包名:$packageName")
        utils.value = packageName
        Logs.e("存储包名后的数据:${utils.value}")

        val utils1 = SpUtils(SpUtils.USER_NAME)
        utils1.value = "author"

        fun1("213213")
    }

    private fun fun1(name: String) {
        val value = "value"
        fun localFun() {
            Logs.e("name is $name,value is $value")
        }
        Logs.e("fun1 complete")
        localFun()
    }

}