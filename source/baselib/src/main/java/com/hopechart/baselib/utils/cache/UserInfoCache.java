package com.hopechart.baselib.utils.cache;

import com.hopechart.baselib.data.UserInfoEntity;

/**
 * @time 2020/5/12
 * @user 张一凡
 * @description 用户信息缓存
 * @introduction 单例模式，声明周期跟随整个APP的声明周期
 */
public class UserInfoCache {

    private UserInfoEntity userInfoEntity;

    private static UserInfoCache instance;

    private UserInfoCache(){

    }

    //获取单例
    public static UserInfoCache getInstance(){
        if(instance == null)
            instance = new UserInfoCache();
        return instance;
    }

    //缓存用户信息
    public void cacheUserInfo(UserInfoEntity entity){
        this.userInfoEntity = entity;
    }

    //获取缓存的用户信息
    public UserInfoEntity getCacheUser(){
        return userInfoEntity;
    }
}
