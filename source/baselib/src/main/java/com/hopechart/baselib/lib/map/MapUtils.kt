package com.hopechart.baselib.lib.map

import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.CustomMapStyleOptions
import com.amap.api.maps.model.MyLocationStyle
import com.hopechart.baselib.utils.Logs

/**
 *@time 2020/5/3
 *@user 张一凡
 *@description
 *@introduction
 * @param locationMe 是否定位到自己的位置
 */
class MapUtils(
    private val mapView: MapView,
    private val locationMe: Boolean = true
) : AMap.OnMyLocationChangeListener, AMap.CancelableCallback {

    //地图视角控制
    private val perspectiveController by lazy {
        MapPerspectiveController(mAMap, this)
    }

    //地图管理控制器
    val mAMap: AMap = mapView.map

    //控制地图上UI交互的控件，如放大缩小按钮等
    private val mUiSettings: UiSettings = mAMap.uiSettings

    init {
        //定位到用户当前位置
        setLocationStyle()
        //默认不显示放大缩小按钮
        hiddenZoomControl()
    }

    override fun onMyLocationChange(location: Location?) {
    }


    //设置定位蓝点
    private fun setLocationStyle() {
        val myLocationStyle = MyLocationStyle()
        //显示当前位置的蓝点
        myLocationStyle.showMyLocation(true)
        //设置连续定位，定位间隔5分钟
        if (locationMe) {
            //myLocationStyle.interval(5 * 60 * 1000)
            //只定位一次
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        }else{
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW)
        }
        mAMap.myLocationStyle = myLocationStyle
        //监听定位信息
        mAMap.setOnMyLocationChangeListener(this)
        mAMap.isMyLocationEnabled = locationMe
    }


    //切换地图图层
    //显示实时路况图层
    fun showTrafficMap() {
        mAMap.isTrafficEnabled = true
    }

    //隐藏实时路况图层
    fun hiddenTrafficMap() {
        mAMap.isTrafficEnabled = false
    }

    //显示导航地图
    fun showNaviMap() {
        showMapWithType(AMap.MAP_TYPE_NAVI)
    }

    //显示夜间地图
    fun showNightMap() {
        showMapWithType(AMap.MAP_TYPE_NIGHT)
    }

    //普通地图，即白昼地图
    fun showNormalMap() {
        showMapWithType(AMap.MAP_TYPE_NORMAL)
    }

    //显示卫星地图
    fun showSatelliteMap() {
        showMapWithType(AMap.MAP_TYPE_SATELLITE)
    }

    //显示指定类型的地图
    private fun showMapWithType(type: Int) {
        mAMap.mapType = type
    }

    //设置地图语言
    //注意英文地图效果不好，不信可以自己看~~
    fun showMapLanguage(isEnglish: Boolean) {
        mAMap.setMapLanguage(if (isEnglish) AMap.ENGLISH else AMap.CHINESE)
    }

    //显示缩放按钮
    fun showZoomControl() {
        mUiSettings.isZoomControlsEnabled = true
    }

    //隐藏缩放按钮
    fun hiddenZoomControl() {
        mUiSettings.isZoomControlsEnabled = false
    }

    //显示指南针按钮
    fun showCompass() {
        mUiSettings.isCompassEnabled = true
    }

    //隐藏指南针按钮
    fun hiddenCompass() {
        mUiSettings.isCompassEnabled = false
    }

    //显示定位按钮
    fun showMyLocationUi() {
        mUiSettings.isMyLocationButtonEnabled = true
        //点击可触发定位
        mAMap.isMyLocationEnabled = true
    }

    //隐藏定位按钮
    fun hiddenMyLocationUi() {
        mUiSettings.isMyLocationButtonEnabled = false
        mAMap.isMyLocationEnabled = false
    }

    //手势操作
    //开启缩放手势
    fun openZoomGesture() {
        mUiSettings.isZoomGesturesEnabled = true
    }

    //关闭缩放手势
    fun closeZoomGesture() {
        mUiSettings.isZoomGesturesEnabled = false
    }

    //开启滑动手势
    fun openScrollGesture() {
        mUiSettings.isScrollGesturesEnabled = true
    }

    //关闭滑动手势
    fun closeScrollGesture() {
        mUiSettings.isScrollGesturesEnabled = false
    }

    //开启旋转手势
    fun openRotateGesture() {
        mUiSettings.isRotateGesturesEnabled = true
    }

    //关闭旋转手势
    fun closeRotateGesture() {
        mUiSettings.isRotateGesturesEnabled = false
    }

    //开启倾斜手势
    fun openTiltGesture() {
        mUiSettings.isTiltGesturesEnabled = true
    }

    //关闭倾斜手势
    fun closeTiltGesture() {
        mUiSettings.isTiltGesturesEnabled = false
    }

    //指定屏幕中心点,设置这个属性后手势操作将会以这个指定的点为中心进行操作
    fun setScreenCenterPoint(x: Int, y: Int) {
        mAMap.setPointToCenter(x, y)
    }

    //开启以屏幕中心点进行手势操作
    fun openGestureByCenter() {
        mUiSettings.isGestureScaleByMapCenter = true
    }

    //关闭以屏幕中心点进行手势操作
    fun closeGestureByCenter() {
        mUiSettings.isGestureScaleByMapCenter = false
    }

    //设置针对AMap的摄像头改变事件
    fun setOnCameraChangeListener(listener: AMap.OnCameraChangeListener) {
        mAMap.setOnCameraChangeListener(listener)
    }

    //设置针对AMap的手势操作事件
    fun setOnMapTouchListener(listener: AMap.OnMapTouchListener) {
        mAMap.setOnMapTouchListener(listener)
    }

    //地图视角变化结束监听
    override fun onFinish() {

    }

    //地图视角变化取消监听
    override fun onCancel() {

    }

}