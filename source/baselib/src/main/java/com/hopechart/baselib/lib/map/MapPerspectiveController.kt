package com.hopechart.baselib.lib.map

import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng

/**
 *@time 2020/5/3
 *@user 张一凡
 *@description 地图视角控制类，主要用于修改地图移动相关的信息
 *@introduction 注意这里默认使用动画的方式移动地图，使用默认的动画执行时间，可扩展此类实现其它方式
 */
class MapPerspectiveController(private val aMap: AMap) {

    private var callback: AMap.CancelableCallback? = null

    //如果希望拿到回调则可以使用这个构造方法
    constructor(aMap: AMap, callback: AMap.CancelableCallback) : this(aMap){
        this.callback = callback
    }


    //设置地图的旋转角度
    fun changeBearing(bearing: Float){
        val factory = CameraUpdateFactory.changeBearing(bearing)
        aMap.animateCamera(factory,this.callback)
    }

    //设置地图的中心点
    fun changeMapCenterLatLng(latitude: Double,longitude: Double){
        val latlng : LatLng = LatLng(latitude,longitude)
        animatePoint(latlng)
    }

    //移动地图到某一个位置
    fun animatePoint(latLng: LatLng,listener: AMap.CancelableCallback? = null){
        val factory = CameraUpdateFactory.changeLatLng(latLng)
        aMap.animateCamera(factory,this.callback)
    }

    //设置地图新的中心点
    fun changeNewMapCenterLatLng(latitude: Double,longitude: Double){
        val latlng : LatLng = LatLng(latitude,longitude)
        val factory = CameraUpdateFactory.newLatLng(latlng)
        aMap.animateCamera(factory,this.callback)
    }

    //设置地图倾斜度
    fun changeTilt(tilt: Float){
        val factory = CameraUpdateFactory.changeTilt(tilt)
        aMap.animateCamera(factory,this.callback)
    }


    //设置地图的缩放级别 3 ~ 19
     fun changeZoom(zoom: Float){
        val factory = CameraUpdateFactory.zoomTo(zoom)
        aMap.animateCamera(factory,this.callback)
    }

    //放大地图级别，在当前级别上加一
    fun zoomIn(){
        val factory = CameraUpdateFactory.zoomIn()
        aMap.animateCamera(factory,this.callback)
    }

    //缩小地图级别，在当前的界别上减1
    fun zoomOutAnimate(){
        val factory = CameraUpdateFactory.zoomOut()
        aMap.animateCamera(factory,this.callback)
    }

    //不适用动画缩小地图级别
    fun zoomOut(){
        val factory = CameraUpdateFactory.zoomOut()
        aMap.moveCamera(factory)
    }

    //同时设置缩放比例和新的中心位置
    fun changeNewLocation(latLng: LatLng,zoom: Float){
        val factory = CameraUpdateFactory.newLatLngZoom(latLng,zoom)
        aMap.moveCamera(factory)
    }

    //使用动画同时设置缩放比例和新的中心位置
    fun changeNewLocationAnimate(latLng: LatLng,zoom: Float){
        val factory = CameraUpdateFactory.newLatLngZoom(latLng,zoom)
        aMap.animateCamera(factory,this.callback)
    }
}