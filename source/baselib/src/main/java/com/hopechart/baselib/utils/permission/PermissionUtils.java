package com.hopechart.baselib.utils.permission;


import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hopechart.baselib.utils.Logs;

/**
*  @intro 一个简单的权限请求工具类
*  @author zyf
*  @date 2019/9/3
*  @descrption
*  @version 1.0
*/
public class PermissionUtils {

    public static final int REQUEST_PERMISSION_CODE = 0x100;
    private static final String TAG = PermissionUtils.class.getSimpleName();
    private static PermissionUtils mPermissionUtils;
    private PermissionResultListener mResultListener;
    private PermissionFragment mFragment;

    private PermissionUtils(){

    }

    public static PermissionUtils getInstance(){
        if(mPermissionUtils == null){
            mPermissionUtils = new PermissionUtils();
        }
        return mPermissionUtils;
    }

    public void with(FragmentManager manager, String[] permissions){
        if(manager == null){
            if(mResultListener != null){
                mResultListener.requestPermissionFailed("需要fragmentManager");
            }
            return;
        }
        if(permissions == null || permissions.length == 0){
            if(mResultListener != null){
                mResultListener.requestPermissionFailed("待请求的权限列表为空，无需请求权限");
            }
            return;
        }

        if(mResultListener == null){
            Logs.e("没有设置回调接口，如果需要获取权限回调，请设置回调接口");
        }
        //查看当前的版本号是否需要申请权限
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if(mResultListener != null){
                mResultListener.requestPermissionSuccess();
            }
            Log.i(TAG,"版本号小于6.0，无需申请权限");
            return;
        }

        mFragment = new PermissionFragment();
        if(mResultListener != null){
            mFragment.setResultListener(mResultListener);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if(!mFragment.isAdded()) {
            transaction.add(mFragment, TAG).commitNow();
        }
        mFragment.requestPermissions(permissions,REQUEST_PERMISSION_CODE);
    }

    //设置接口
    public void setPermissionResultListener(PermissionResultListener resultListener){
        this.mResultListener = resultListener;
    }

    public PermissionResultListener getResultListener(){
        return mResultListener;
    }


}
