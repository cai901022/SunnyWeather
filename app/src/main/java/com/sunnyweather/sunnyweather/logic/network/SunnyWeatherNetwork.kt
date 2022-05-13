package com.sunnyweather.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  统一的网络数据源访问入口
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/
object SunnyWeatherNetwork {

    //获取PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    //发起搜索城市数据请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //获取WeatherService接口的动态代理对象
    private val weatherService = ServiceCreator.create(WeatherService::class.java)
    //发起实时天气信息数据请求
    suspend fun getRealtimeWeather(lng: String,lat: String) = weatherService.getRealtimeWeather(lng,lat).await()
    //发起当地未来的几天信息数据请求
    suspend fun getDailyWeather(lng: String,lat: String) = weatherService.getDailyWeather(lng,lat).await()

    //协程简化回调的通用写法
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}