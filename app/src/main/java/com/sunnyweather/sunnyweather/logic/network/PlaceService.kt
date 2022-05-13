package com.sunnyweather.sunnyweather.logic.network

import com.sunnyweather.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc: 访问彩云天气城市搜索API的Retrofit接口
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/
interface PlaceService {

    //https://api.caiyunapp.com/v2/place?query=北京&token={token}&lang=zh_CN
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}