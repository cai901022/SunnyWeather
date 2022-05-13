package com.sunnyweather.sunnyweather.logic.model

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  天气数据信息类
 * @Author:  Caixiaojia
 * @Date:  2022/5/16
 **************************************************************/
data class Weather (val realtime:RealtimeResponse.Realtime,val daily:DailyResponse.Daily)
