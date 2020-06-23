package com.hopechart.baselib.lib.map

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.*


/**
 *@time 2020/5/15
 *@user 张一凡
 *@description 地理位置和经纬度互转的工具类
 *@introduction
 */
class MapGeoCodingUtils(
    context: Context
) : GeocodeSearch.OnGeocodeSearchListener {


    //地址转坐标调用这个LiveData
    val latLonLiveData = MutableLiveData<LatLonPoint>()

    //坐标转地址调用这个LiveData
    val addressLiveData = MutableLiveData<String>()

    //地址转坐标调用这个构造方法
    constructor(context: Context,address: String,city: String?): this(context){
        addressToLat(address,city)
    }

    //坐标转地址调用这个构造方法
    constructor(context: Context,latLng: LatLonPoint): this(context){
        latToAddress(latLng)
    }

    private val mGeocodeSearch: GeocodeSearch by lazy {
        GeocodeSearch(context)
    }

    //将地址转换为坐标
    private fun addressToLat(address: String, city: String?) {
        mGeocodeSearch.setOnGeocodeSearchListener(this)
        val query = GeocodeQuery(address, city)
        mGeocodeSearch.getFromLocationNameAsyn(query)
    }

    //将坐标转换为地址
    private fun latToAddress(lat: LatLonPoint) {
        mGeocodeSearch.setOnGeocodeSearchListener(this)
        val query = RegeocodeQuery(lat, 200.toFloat(), GeocodeSearch.AMAP)
        mGeocodeSearch.getFromLocationAsyn(query)
    }

    override fun onRegeocodeSearched(result: RegeocodeResult?, p1: Int) {
        addressLiveData.value = result?.regeocodeAddress?.formatAddress
    }

    override fun onGeocodeSearched(result: GeocodeResult?, code: Int) {
        latLonLiveData.value = result?.geocodeAddressList?.get(0)?.latLonPoint
    }
}