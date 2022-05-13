package com.sunnyweather.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.sunnyweather.logic.Repository
import com.sunnyweather.sunnyweather.logic.model.Location
import com.sunnyweather.sunnyweather.logic.model.Place

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  Weather的ViewModel层
 * @Author:  Caixiaojia
 * @Date:  2022/5/16
 **************************************************************/
class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()  //坐标数据

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){ location ->
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng: String,lat: String){
        locationLiveData.value = Location(lng,lat)
    }
}