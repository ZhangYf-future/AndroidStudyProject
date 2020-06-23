package com.hopechart.baselib.lib.map

import android.graphics.Color
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng

/**
 *@time 2020/5/8
 *@user 张一凡
 *@description 地图上绘制线段的控制类
 *@introduction
 */
class PolylineController(private val amap: AMap) {
    //线段的颜色
    private var mColor: Int = Color.parseColor("#000000")
    //线段的宽度
    private var mWidth: Float = 2.0f
    //是否是虚线，默认false
    private var mIsDottedLine: Boolean = false
    //是否是大地曲线，默认为false
    private var mIsGeodesic: Boolean = false
    //线段的点集合
    private val mLines = mutableListOf<LatLng>()

    //使用默认属性绘制线段
    fun drawLine(){

    }

}