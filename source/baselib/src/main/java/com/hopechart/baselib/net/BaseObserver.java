package com.hopechart.baselib.net;

import android.text.TextUtils;

import com.google.gson.JsonIOException;
import com.hopechart.baselib.BaseApplication;
import com.hopechart.baselib.data.BaseData;
import com.hopechart.baselib.mvvm.BaseViewModel;
import com.hopechart.baselib.utils.Logs;

import org.json.JSONException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @time 2020/4/27
 * @user 张一凡
 * @description 可以在这里对错误码进行统一的处理
 * @introduction
 */
public abstract class BaseObserver<T extends BaseData> implements Observer<T> {
    //200为请求成功
    protected static final int SUCCESS_CODE = 200;

    //401未授权
    protected static final int UNAUTHORIZED = 401;

    //400请求出错
    protected static final int BAD_REQUEST = 400;

    //403请求被禁止
    protected static final int FORBIDDEN = 403;

    //404未找到
    protected static final int NOT_FOUND = 404;

    //方法不允许
    protected static final int METHOD_NOT_ALLOW = 405;

    //请求超时
    protected static final int REQUEST_TIME_OUT = 408;

    //服务器内部错误
    protected static final int SERVER_ERROR = 500;

    //出现异常
    protected static final int ERROR_EXCEPTION = -400;

    //HTTP异常
    protected static final int HTTP_EXCEPTION = -1000;

    //JSON异常
    protected static final int JSON_EXCEPTION = 2000;

    //其它异常信息
    protected static final int OTHER_EXCEPTION = 3000;

    //这里可以接收一个ViewModel,
    protected BaseViewModel viewModel;

    public BaseObserver() {

    }

    public BaseObserver(BaseViewModel viewModel) {
        this(viewModel,true);
    }

    //通过接收一个BaseViewModel来在不同时机调用不同的状态
    public BaseObserver(BaseViewModel viewModel,boolean autoShowLoading){
        this.viewModel = viewModel;
        if(autoShowLoading){
            //如果允许，则直接调用显示加载框的方法
            this.viewModel.requestStart();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        //请求完成
        if (t.getCode() == SUCCESS_CODE) {
            //返回200，表示请求成功，但是这里是否请求到数据并不一定
            success(t);
        } else {
            String error;
            switch (t.getCode()) {
                case UNAUTHORIZED:
                    error = "401未授权";
                    break;
                case BAD_REQUEST:
                    error = "400请求出错";
                    break;
                case FORBIDDEN:
                    error = "403请求被禁止";
                    break;
                case NOT_FOUND:
                    error = "404未找到";
                    break;
                case METHOD_NOT_ALLOW:
                    error = "405方法不被允许";
                    break;
                case REQUEST_TIME_OUT:
                    error = "408请求超时";
                    break;
                case SERVER_ERROR:
                    error = "500服务器内部错误";
                    break;
                default:
                    error = "未知错误";
                    break;
            }
            error = TextUtils.isEmpty(t.getMessage()) ? error : t.getMessage();
            onDataError(t.getCode(),error);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(e == null){
            //未知异常信息
            onDataError("请求出错，未知异常");
        }else if(e instanceof HttpException){
            //网络错误
            onNetError();
        }else if(e instanceof JSONException){
            //数据转换异常
            onDataError(JSON_EXCEPTION,"数据转换出错");
        }else{
            //其它异常
            onDataError(OTHER_EXCEPTION,e.getMessage() != null ? e.getMessage() : "");
        }

    }

    @Override
    public void onComplete() {

    }

    //数据请求成功
    private void success(T t) {
        if (t.getData() == null) {
            onDataEmpty();
            return;
        }
        onDataSuccess(t);
    }

    //只有错误信息的方法
    public void onDataError(String message){
        onDataError(-1,message);
    }

    //带有错误码的出错信息
    public void onDataError(int code,String message){
        Logs.e("请求出错 "+ code +","+message);
        if(code == UNAUTHORIZED){
            Logs.e("会话数据失效");
            BaseApplication.getInstance().cookieExpireListener.cookieExpire();
            return;
        }
    }

    //网络错误
    public abstract void onNetError();

    //请求成功code == 200，但是请求到的数据中的data == null 时调用此方法
    public abstract void onDataEmpty();

    //请求成功，并且请求到的数据中的data != null时调用此方法
    public abstract void onDataSuccess(T data);
}
