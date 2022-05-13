package com.sunnyweather.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.sunnyweather.logic.Repository
import com.sunnyweather.sunnyweather.logic.model.Place

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc: Place的ViewModel层
 * @Author:  Caixiaojia
 * @Date:  2022/5/13
 **************************************************************/
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()  //定义一个placeList的集合，用于对界面上显示的城市数据进行缓存(反之屏幕旋转丢失)

    val placeLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchPlaces(query)
    }

    //每当searchPlaces()函数被调用时，switchMap()方法所对应的转换函数就会执行
    fun searchPlaces(query:String){
        searchLiveData.value = query
    }

    //封装SharedPreferences的读写操作
    fun savePlace(place:Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()
}