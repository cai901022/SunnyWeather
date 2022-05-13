package com.sunnyweather.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.sunnyweather.logic.model.Place

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:
 * @Author:  Caixiaojia
 * @Date:  2022/5/17
 **************************************************************/
object PlaceDao {

    fun savePlace(place: Place){
        sharedPreferences().edit(){
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)//解析成Place对象
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")  //判断数据是否已存储

    //SharedPreferences文件存放在/data/data/<package name>/shared_prefs/目录下的"sunny_weather"文件
    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}