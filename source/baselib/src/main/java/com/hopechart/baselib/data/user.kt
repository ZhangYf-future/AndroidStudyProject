package com.hopechart.baselib.data

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.hopechart.baselib.BR

/**
 *@time 2020/5/11
 *@user 张一凡
 *@description 和用户相关的信息
 *@introduction
 */

//用户登录信息，包括登录名称和密码
class UserLoginEntity(
    account: String,
    password: String): BaseObservable() {

    @get:Bindable
    var account: String = account
        set(value) {
            field = value
            notifyPropertyChanged(BR.account)
        }

    @get: Bindable
    var password: String = password
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    override fun equals(o: Any?): Boolean {
        val other = o as UserLoginEntity
        return TextUtils.equals(account, other.account)
    }

    override fun toString(): String {
        return account
    }

    override fun hashCode(): Int {
        return account.hashCode()
    }
}


//用户信息
data class UserInfoEntity(
    val address: String,
    val cityId: Int,
    val cityName: String,
    val createTime: String,
    val createUser: String,
    val createUserName: String,
    val email: String,
    val expiryTime: String,
    val headOrgId: String,
    val id: Int,
    val lastLoginTime: String,
    val lockAuthorizedPwd: String,
    val loginName: String,
    val orderSqlStr: String,
    val provinceId: String,
    val provinceName: String,
    val pwd: String,
    val pwdState: Int,
    val remark: String,
    val roleId: String,
    val roleName: String,
    val rows: Int,
    val state: Int,
    val telephone: String,
    val tenantId: String,
    val updateTime: String,
    val updateUser: String,
    val updateUserName: String,
    val userId: String,
    val userName: String,
    val userType: Int,
    val language: String,
    val orgUuid: String,
    val craneSessionId: String,//请求重起接口时候的sessionId
    val cranecloudBaseUrl: String //请求重起接口时候的baseUrl
)

//系统配置项
data class SystemConfigItemEntity(
    val language: String,
    val orderSqlStr: String,
    val typeName: String,
    val typeRemark: String,
    val typeVal: String
)