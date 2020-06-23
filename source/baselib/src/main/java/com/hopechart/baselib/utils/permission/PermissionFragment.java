package com.hopechart.baselib.utils.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionFragment extends Fragment {

    private static final String TAG = PermissionFragment.class.getSimpleName();

    private PermissionResultListener mResultListener;

    public void setResultListener(PermissionResultListener resultListener){
        this.mResultListener = resultListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG,"获取到权限回调:"+(requestCode == PermissionUtils.REQUEST_PERMISSION_CODE));
        FragmentActivity activity = getActivity();
        if(requestCode == PermissionUtils.REQUEST_PERMISSION_CODE && activity != null){
            //保存用户拒绝的权限
            List<String> denialPermissions = new ArrayList<>();
            for(int i = 0; i < permissions.length; i++){
                Log.i(TAG,"permission :" + permissions[i] + "," +(grantResults[i] != PackageManager.PERMISSION_GRANTED));
                if(activity.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED){
                    denialPermissions.add(permissions[i]);
                }
            }
            Log.i(TAG,"拒绝的权限数："+denialPermissions.size()+","+mResultListener);
            if(mResultListener != null){
                if(denialPermissions.isEmpty()){
                    mResultListener.requestPermissionSuccess();
                }else{
                    mResultListener.requestPermissionFailed(denialPermissions);
                }
            }
        }
    }
}
