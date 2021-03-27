package com.project.mystudyproject.rx_java_study

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivityRxJavaCreatorOperatorBinding
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class RxJavaCreatorOperatorActivity : BaseActivity<ActivityRxJavaCreatorOperatorBinding>() {

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_rx_java_creator_operator

    //点击事件
    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_create -> {
                observableWithCreate()
            }

            R.id.btn_just ->
                observableWithJust()

            R.id.btn_from_array ->
                observableWithFromArray()

            R.id.btn_from_iterable ->
                observableWithFromIterable()

            R.id.btn_never ->
                observableWithNever()

            R.id.btn_empty ->
                observableWithEmpty()

            R.id.btn_error ->
                observableWithError()

            R.id.btn_defer ->
                observableWithDefer()

            R.id.btn_timer ->
                observableWithTimer()

            R.id.btn_interval ->
                observableWithInterval()

            R.id.btn_interval_range ->
                observableWithIntervalRange()

            R.id.btn_range ->
                observableWithRange()

            R.id.btn_range_long ->
                observableWithRangeLong()

        }
    }

    //使用`create()`创建被观察者
    @SuppressLint("CheckResult")
    private fun observableWithCreate() {

        Observable.create(object : ObservableOnSubscribe<Int> {
            @SuppressLint("CheckResult")
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                for (i in 0 until 10)
                    emitter.onNext(i)
                emitter.onComplete()
            }
        }).subscribe {
            Logs.e("observableWithCreate onNext:$it")
        }

    }

    //使用`just()`创建被观察者
    private fun observableWithJust() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .subscribe({
                Logs.e("observableWithJust onNext: $it")
            }, {
                Logs.e("observableWithJust onError: $it")
            }, {
                Logs.e("observableWithJust onComplete...")
            }, {
                Logs.e("observableWithJust 执行onSubscribe()方法:$it")
            })
    }

    /**
     * 使用[Observable.fromArray]创建被观察者
     */
    private fun observableWithFromArray() {
        val intArray = arrayOf<Int>(1, 2, 3, 4, 5, 6, 7)
        Observable.fromArray(*intArray)
            .subscribe({
                Logs.e("observableWithFromArray onNext:$it")
            }, {
                Logs.e("observableWithFromArray onError:$it")
            }, {
                Logs.e("observableWithFromArray onComplete")
            }, {
                Logs.e("observableWithFromArray 执行onSubscribe()方法:$it")
            })
    }

    /**
     * 使用[Observable.fromIterable]创建被观察者
     */
    private fun observableWithFromIterable() {
        val list = mutableListOf<String>("2321", "123123", "ASAs")
        Observable.fromIterable(list)
            .subscribe({
                Logs.e("observableWithFromIterable onNext:$it")
            }, {
                Logs.e("observableWithFromIterable onError:$it")
            }, {
                Logs.e("observableWithFromIterable onComplete...")
            }, {
                Logs.e("observableWithFromIterable 开始执行onSubscribe()方法")
            })

    }


    private val mObserver = object : Observer<Any?> {
        override fun onSubscribe(d: Disposable) {
            Logs.e("observableWithNever onSubscribe...$d")
        }

        override fun onError(e: Throwable) {
            Logs.e("observableWithNever onError...$e")
        }

        override fun onComplete() {
            Logs.e("observableWithNever onComplete...")
        }

        override fun onNext(t: Any) {
            Logs.e("observableWithNever onNext...$t")
        }
    }

    /**
     * 使用[Observable.never]创建被观察者
     */
    private fun observableWithNever() {
        Observable.never<Any?>()
            .subscribe(mObserver)
    }

    /**
     * 使用[Observable.empty]创建被观察者
     */
    private fun observableWithEmpty() {
        Observable.empty<Any>()
            .subscribe(mObserver)
    }

    /**
     * 使用[Observable.error]创建被观察者
     */
    private fun observableWithError() {
        Observable.error<IllegalArgumentException>(IllegalArgumentException("仅仅是测试error()方法"))
            .subscribe(mObserver)
    }

    /**
     * 使用[Observable.defer]创建被观察者
     */
    private fun observableWithDefer() {
        //开始的数据
        var startNum = 10
        //通过defer创建被观察者,需要返回一个Observable对象，这里使用just
        var deferObservable = Observable.defer(object : Callable<ObservableSource<Int>> {
            override fun call(): ObservableSource<Int> {
                Logs.e("创建Observable")
                return Observable.just(startNum)
            }
        })
        //重新对数据进行赋值
        startNum = 100
        //创建观察者并开始订阅
        var observer = object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Logs.e("observableWithDefer 开始订阅:onSubscribe():$d")

            }

            override fun onNext(t: Int) {
                Logs.e("observableWithDefer 获取到数据$t")
            }

            override fun onError(e: Throwable) {
                Logs.e("observableWithDefer onError:$e")
            }

            override fun onComplete() {
                Logs.e("observableWithDefer onComplete....")
            }

        }
        Logs.e("开始订阅...")
        //开始订阅
        deferObservable.subscribe(observer)
    }

    /**
     * 使用[Observable.timer]创建被观察者
     */
    private fun observableWithTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
            .subscribe({
                Logs.e("observableWithTimer onNext:$it")
            }, {
                Logs.e("observableWithTimer onError:$it")
            }, {
                Logs.e("observableWithTimer onComplete...")
            }, {
                if (it == null) {
                    Logs.e("Disposable为空")
                }
                Logs.e("observableWithTimer 开始订阅:${it.string()}")
            })
    }

    /**
     * 使用[Observable.interval]创建被观察者
     */
    private fun observableWithInterval() {
        var disposable: Disposable? = null
        var num: Long = -1
        Observable.interval(3, TimeUnit.SECONDS)
            .subscribe({
                num = it
                Logs.e("observableWithInterval onNext: $it")
                if (num > 20)
                    disposable?.dispose()
            }, {
                Logs.e("observableWithInterval onError: $it")
            }, {
                Logs.e("observableWithInterval onComplete...")
            }, {
                disposable = it
                Logs.e("observableWithInterval onSubscribe:$it")
            })
    }

    /**
     * 使用[Observable.intervalRange]创建被观察者
     */
    private fun observableWithIntervalRange() {
        Observable.intervalRange(10, 6, 3, 1, TimeUnit.SECONDS)
            .subscribe({
                Logs.e("observableWithIntervalRange onNext: $it")
            }, {
                Logs.e("observableWithIntervalRange onError:$it")
            }, {
                Logs.e("observableWithIntervalRange onComplete...")
            }, {
                Logs.e("observableWithIntervalRange onSubscribe:$it")
            })
    }

    /**
     * 使用[Observable.range]创建被观察者
     */
    private fun observableWithRange() {
        Observable.range(100, 10)
            .subscribe({
                Logs.e("observableWithRange onNext: $it")
            }, {
                Logs.e("observableWithRange onError:$it")
            }, {
                Logs.e("observableWithRange onComplete...")
            }, {
                Logs.e("observableWithRange onSubscribe:$it")
            })
    }

    /**
     * 使用[Observable.rangeLong]创建被观察者
     */
    private fun observableWithRangeLong() {
        Observable.rangeLong(100, 10)
            .subscribe({
                Logs.e("observableWithRangeLong onNext: $it,type is${it::class.java.name}")
            }, {
                Logs.e("observableWithRangeLong onError:$it")
            }, {
                Logs.e("observableWithRangeLong onComplete...")
            }, {
                Logs.e("observableWithRangeLong onSubscribe:$it")
            })
    }
}

private fun Disposable.string(): String {
    return this::class.java.name
}

