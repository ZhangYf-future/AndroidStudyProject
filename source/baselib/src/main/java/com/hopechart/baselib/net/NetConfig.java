package com.hopechart.baselib.net;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hopechart.baselib.BaseApplication;
import com.hopechart.baselib.net.gson_config.BooleanTypeAdapter;
import com.hopechart.baselib.net.gson_config.DoubleTypeAdapter;
import com.hopechart.baselib.net.gson_config.IntegerTypeAdapter;
import com.hopechart.baselib.net.gson_config.StringNullTypeAdapter;
import com.hopechart.baselib.utils.AppManager;
import com.hopechart.baselib.utils.ConfigUtils;
import com.hopechart.baselib.utils.Logs;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @time 2020/4/23
 * @user 张一凡
 * @description 网络配置类
 * @introduction
 */
public class NetConfig {
    /**
     * 缓存大小,默认10M
     */
    private static int CACHE_SIZE = 10 * 1024 * 1024;
    //retrofit实例
    private static Retrofit retrofit;

    //不同版本请求的地址
    private static final String API_DEVELOP = "http://202.96.122.219:18080/api/";
    private static final String API_DEBUG = "http://202.96.122.219:18080/api/";
    private static final String API_RELEASE = "http://202.96.122.219:18080/api/";

    //获取不同版本的地址
    public static String getApi() {
        if (ConfigUtils.config.equals("develop"))
            return API_DEVELOP;
        else if (ConfigUtils.config.equals("release"))
            return API_RELEASE;
        else
            return API_DEBUG;
    }

    //初始化Retrofit
    private static synchronized void initRetrofit() {
        if (retrofit != null) {
            throw new IllegalStateException("Retrofit已完成初始化");
        }
        //创建gson转换器
        Gson gson = new GsonBuilder()
                //格式化日期
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                //有时候返回int,double等类型会返回空值，在这里统一处理
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
                .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
                //String为空时返回""
                .registerTypeAdapter(String.class, new StringNullTypeAdapter())
                .create();

        //Http日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logs.e(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //cache
        Cache cache = new Cache(AppManager.getApp().getCacheDir(),CACHE_SIZE);
        //cookie
        ClearableCookieJar cookieJar = new MyCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getInstance()));
        //创建OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder()
                //默认连接超时时间10秒
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                //cookie管理
                .cookieJar(cookieJar)
                //这里不会设置readTimeOut,因为有的接口返回数据量很大，时间很难控制
                //日志打印
                .addInterceptor(interceptor)
                //替换baseUrl
                .addInterceptor(new BaseUrlChangeInterceptor())
                //缓存
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getApi())
                .client(httpClient)
                //数据转换
                .addConverterFactory(GsonConverterFactory.create(gson))
                //数据转换之后再进行包装成对应的bean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //获取retrofit实例
    //如果有些地方不想使用默认的配置，可以通过这里获取retrofit对象后设置自定义的配置
    public static Retrofit getRetrofit() {
        if (retrofit == null)
            initRetrofit();
        return retrofit;
    }

    //统一在这里创建返回的Retrofit可用的类
    public static <T> T createApi(Class<T> tClass){
        if(tClass == null){
            throw new IllegalStateException("无法创建空的class");
        }
        return getRetrofit().create(tClass);
    }
}
