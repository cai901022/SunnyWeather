package com.sunnyweather.sunnyweather.logic.network

import com.sunnyweather.sunnyweather.SunnyWeatherApplication
import com.sunnyweather.sunnyweather.logic.model.DailyResponse
import com.sunnyweather.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc: 访问彩云天气信息API的Retrofit接口（实时和未来几天）
 * @Author:  Caixiaojia
 * @Date:  2022/5/16
 **************************************************************/
interface WeatherService {

    //https://api.caiyunapp.com/v2.5/{token}/116.4073963,39.9041999/realtime.json
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String,@Path("lat") lat: String): Call<RealtimeResponse>

    //https://api.caiyunapp.com/v2.5/{token}/116.4073963,39.9041999/daily.json
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String,@Path("lat") lat: String): Call<DailyResponse>
}