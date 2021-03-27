package com.project.mystudyproject;

import com.hopechart.baselib.utils.Logs;

import io.reactivex.Observable;

/**
 * @ClassName: TestClass
 * @Author: zyf
 * @Date: 2021/3/25 10:22
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
public class TestClass {

    public static void run(){
        Integer[] array = new Integer[]{1,2,3,4,5,6,7};
        Observable.fromArray(array)
                .subscribe((item)->{
                    Logs.e("onNext:" + item);
                });
    }
}
