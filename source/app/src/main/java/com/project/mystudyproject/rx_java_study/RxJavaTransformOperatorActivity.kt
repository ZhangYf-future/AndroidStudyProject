package com.project.mystudyproject.rx_java_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityRxJavaTransformOperatorBinding
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * @作者 zyf
 * @日期 2021/3/26
 * @说明 RxJava变换操作符学习,包括map(),flatMap(),contactMap(),buffer()
 * @修改信息
 */
class RxJavaTransformOperatorActivity : BaseActivity<ActivityRxJavaTransformOperatorBinding>() {

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_rx_java_transform_operator

    //点击事件
    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_map ->
                transformWithMap()

            R.id.btn_flat_map ->
                transformWithFlatMap()

            R.id.btn_concat_map ->
                transformWithConcatMap()

            R.id.btn_buffer ->
                transformWithBuffer()
        }
    }

    /**
     * 使用[Observable.map]变换
     */
    private fun transformWithMap() {
        Observable.just(1, 2, 3, 4, 5)
            .map(object : Function<Int, String> {
                override fun apply(t: Int): String {
                    return "this is $t"
                }
            })
            .subscribe {
                Logs.e("transformWithMap： $it")
            }
    }

    /**
     * 使用[Observable.flatMap]变换
     */
    private fun transformWithFlatMap() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }
            .flatMap(object : Function<Int, Observable<String>> {
                override fun apply(t: Int): Observable<String> {
                    val array = mutableListOf<String>()
                    for (i in 0 until 3) {
                        array.add("$t--flatMap-->  ${t * 10 + i}")
                    }
                    return Observable.fromIterable(array)
                }
            })
            .subscribe {
                Logs.e("transformWithFlatMap onNext: $it")
            }
    }

    /**
     * 使用[Observable.concatMap]转换事件序列
     */
    private fun transformWithConcatMap() {
        Observable.just(1, 2, 3)
            .concatMap(object : Function<Int, Observable<String>> {
                override fun apply(t: Int): Observable<String> {
                    val list = mutableListOf<String>()
                    for (i in t * 10 until t * 10 + 3) {
                        list.add("$t --concatMap--> $i")
                    }
                    return Observable.fromIterable(list)
                }
            }).subscribe {
                Logs.e("transformWithConcatMap onNext: $it")
            }
    }

    /**
     * 使用[Observable.buffer]转换事件序列
     */
    private fun transformWithBuffer() {
//        Observable.just<Int>(1,2,3,4,5)
//            .buffer(3, 2)
//            .subscribe {
//                Logs.e("transformWithBuffer onNext: $it")
//            }

        Observable.intervalRange(10,100,30,1,TimeUnit.SECONDS)
            .buffer(20,2,TimeUnit.SECONDS)
            .subscribe{
                Logs.e("transformWithBuffer onNext: $it")
            }
    }

}