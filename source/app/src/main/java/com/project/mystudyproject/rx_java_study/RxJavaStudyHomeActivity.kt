package com.project.mystudyproject.rx_java_study

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityRxJavaStudyHomeBinding
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RxJavaStudyHomeActivity : BaseActivity<ActivityRxJavaStudyHomeBinding>() {

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_rx_java_study_home

    //点击事件
    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_basic_implement ->
                this.simpleImplement()

            //创建操作符
            R.id.btn_creator_operator ->
                startActivity(Intent(this, RxJavaCreatorOperatorActivity::class.java))

            //变换操作符
            R.id.btn_transform_operator ->
                startActivity(Intent(this, RxJavaTransformOperatorActivity::class.java))
        }
    }

    //RxJava最简单的事件
    @SuppressLint("CheckResult")
    private fun simpleImplement() {
        //创建被观察者
        val observable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                emitter.onNext("A")
                emitter.onNext("B")
                emitter.onNext("C")
                emitter.onNext("D")
                emitter.onNext("E")
                emitter.onNext("F")
                emitter.onComplete()
            }
        })

        //创建观察者
        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Logs.e("onSubscribe...")
            }

            override fun onNext(t: String) {
                Logs.e("onNext:${t}...")
            }

            override fun onError(e: Throwable) {
                Logs.e("onError:${e}...")
            }

            override fun onComplete() {
                Logs.e("onComplete...")
            }
        }

        //通过订阅连接观察者和被观察者
        observable.subscribe(observer)



        Observable.fromIterable(mutableListOf(1, 2, 3, 4)).subscribe {
            Logs.e("from iterable onNext：$it")
        }


        Observable.just<Int>(1, 2, 3, 4, 5).subscribe {
            Logs.e("onNext：$it")
        }

        val disposable = Observable.fromArray("A", "B", "c").subscribe({
            Logs.e("onNext is:$it")
        }, {
            Logs.e("error:$it")
        }, {
            Logs.e("onComplete")
        })

        disposable.dispose()


        var disposable1: Disposable? = null
        Observable.create<String> {
            for (i in 0 until 10) {
                Logs.e("发送事件:$i")
                it.onNext(i.toString())
            }
            it.onComplete()
        }.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposable1 = d
            }

            override fun onNext(t: String) {
                Logs.e("onNext:$t")
                if (Integer.parseInt(t) == 5 && disposable1?.isDisposed != true) {
                    Logs.e("断开连接")
                    disposable1?.dispose()
                }
            }

            override fun onError(e: Throwable) {
                Logs.e("onError:$e")
            }

            override fun onComplete() {
                Logs.e("onComplete...")
            }
        })

    }


}