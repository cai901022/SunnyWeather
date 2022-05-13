package com.sunnyweather.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc: 全局获取Context类
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/
class SunnyWeatherApplication : Application(){

    companion object{
        const val TOKEN = "XHnvpyAy29bbD5wu"  //天气api申请到的令牌值

        @SuppressLint("StaticFieldLeak")  //忽略将Context设置成静态变量的警告注解
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}