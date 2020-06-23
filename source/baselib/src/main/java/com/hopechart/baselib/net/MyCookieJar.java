package com.hopechart.baselib.net;

import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.hopechart.baselib.data.UserInfoEntity;
import com.hopechart.baselib.utils.Logs;
import com.hopechart.baselib.utils.cache.UserInfoCache;

import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class MyCookieJar extends PersistentCookieJar {

    public MyCookieJar(CookieCache cache, CookiePersistor persistor) {
        super(cache, persistor);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        //获取本地保存的重汽的地址
        UserInfoEntity localUser = UserInfoCache.getInstance().getCacheUser();
        if(localUser !=null && !TextUtils.isEmpty(url.toString()) && !TextUtils.isEmpty(localUser.getCranecloudBaseUrl())){
            if(url.toString().contains(localUser.getCranecloudBaseUrl())){
                //之前已经设置过了，不需要在此处再设置
                return Collections.emptyList();
            }
        }

        return super.loadForRequest(url);
    }
}
