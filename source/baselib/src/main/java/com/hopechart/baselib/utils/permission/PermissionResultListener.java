package com.hopechart.baselib.utils.permission;

import java.util.List;

/**
*  @intro 权限请求的回调结果
*  @author zyf
*  @date 2019/9/3
*  @descrption
*  @version 1.0
*/
public interface PermissionResultListener {
    //权限请求成功
    void requestPermissionSuccess();

    //权限请求失败，返回失败的权限名称
    void requestPermissionFailed(List<String> failedPermissions);

    //权限请求失败，返回失败原因
    void requestPermissionFailed(String errorMessage);
}
