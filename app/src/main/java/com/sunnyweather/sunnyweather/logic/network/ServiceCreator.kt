package com.sunnyweather.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  Retrofit构建器，为了使用PlaceService接口
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/
object ServiceCreator {

    //https://api.caiyunapp.com/v2/place?query=北京&token={token}&lang=zh_CN
    private val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}