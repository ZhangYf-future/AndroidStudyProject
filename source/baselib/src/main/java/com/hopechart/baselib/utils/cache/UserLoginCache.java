package com.hopechart.baselib.utils.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hopechart.baselib.data.UserLoginEntity;
import com.hopechart.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @time 2020/5/11
 * @user 张一凡
 * @description
 * @introduction
 */
// FIXME: 2020/5/11 注意此处在迭代的时候需要修改成数据库的方式
public class UserLoginCache {
    public static final String USER_LOGIN_KEY = "userLogin";

    //获取用户列表
    public static List<UserLoginEntity> getUserLoginList(){
        List<UserLoginEntity> userList = new ArrayList<>();
        String users = getLocalUserStr();
        List<UserLoginEntity> localUsers = new Gson().fromJson(users,new TypeToken<List<UserLoginEntity>>(){}.getType());
        if(localUsers != null && !localUsers.isEmpty())
            userList.addAll(localUsers);
        return userList;
    }

    //获取保存在本地的用户数据
    private static String getLocalUserStr(){
        return SharedPreferencesUtils.getString(USER_LOGIN_KEY,"");
    }


    //添加一个用户信息
    public static void addUser(UserLoginEntity entity){
        List<UserLoginEntity> userList = getUserLoginList();
        if(userList == null) {
            userList = new ArrayList<>();
            userList.add(entity);
        }else{
            //查看是否已经存在当前用户信息
            if(userList.contains(entity)){
                //删除以前的用户信息，将最新的用户保存在本地
                userList.remove(entity);
                userList.add(entity);
            }else{
                userList.add(entity);
            }
        }

        putUserLogin(new Gson().toJson(userList,new TypeToken<List<UserLoginEntity>>(){}.getType()));
    }
    //保存用户信息
    private static void putUserLogin(String user){
        SharedPreferencesUtils.putString(USER_LOGIN_KEY,user);
    }

    //删除一个用户信息
    public static void deleteUser(UserLoginEntity entity){
        List<UserLoginEntity> userList = getUserLoginList();
        if(userList == null)
            return;
        userList.remove(entity);
        putUserLogin(new Gson().toJson(userList,new TypeToken<List<UserLoginEntity>>(){}.getType()));
    }

    //清空用户信息
    public static void deleteAllUser(){
        putUserLogin("");
    }
}
