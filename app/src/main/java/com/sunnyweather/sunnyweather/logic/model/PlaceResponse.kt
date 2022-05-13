package com.sunnyweather.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  数据模型
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/

//查询城市数据信息
data class PlaceResponse(val status:String, val places:List<Place>)

//位置数据，城市名，坐标 //@SerializedName注解，因JSON中一些字段的命名可能和Kotlin的命名规范不太一致
data class Place(val name:String, val location: Location,
                 @SerializedName("formatted_address") val address: String)

//坐标数据
data class Location(val lng:String,val lat:String)