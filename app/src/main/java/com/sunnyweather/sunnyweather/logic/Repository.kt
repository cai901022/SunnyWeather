package com.sunnyweather.sunnyweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.sunnyweather.sunnyweather.logic.dao.PlaceDao
import com.sunnyweather.sunnyweather.logic.model.DailyResponse
import com.sunnyweather.sunnyweather.logic.model.Place
import com.sunnyweather.sunnyweather.logic.model.Weather
import com.sunnyweather.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:  仓库层的统一封装入口
 * @Author:  Caixiaojia
 * @Date:  2022/5/12
 **************************************************************/
object Repository {

    /*方法一
    //构建一个liveData， //Dispatchers.IO,代码块中的所有代码都运行在子线程中
    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try{
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status == "ok") //返回值里面的status关键字值
            {
                val places= placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    //刷新天气(实时和未来几天)
    fun refreshWeather(lng: String,lat: String) = liveData(Dispatchers.IO){
        val result = try {
            //创建协程作用域
            coroutineScope {
                val deferredRealtime = async{
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily = async{
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                //等待协程同步完成
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                //判断回包
                if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                }else {
                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"+
                            "daily response status is ${dailyResponse.status}"))
                }
            }
        }
        catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }

     */

    /*
    方法二
     */

    //构建一个liveData， //Dispatchers.IO,代码块中的所有代码都运行在子线程中
    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//        Log.d("Repository","placeResponse=$placeResponse")
//        Log.d("Repository","status=${placeResponse.status}")
        if(placeResponse.status == "ok") //返回值里面的status关键字值
        {
//            Log.d("Repository","places=${placeResponse.places}")
            val places= placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    //刷新天气(实时和未来几天)
    fun refreshWeather(lng: String,lat: String) = fire(Dispatchers.IO){
        //创建协程作用域
        coroutineScope {
            val deferredRealtime = async{
                SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
            }
            val deferredDaily = async{
                SunnyWeatherNetwork.getDailyWeather(lng,lat)
            }
            //等待协程同步完成
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            Log.d("Repository","realtimeResponse=$realtimeResponse")
            Log.d("Repository","dailyResponse=$dailyResponse")
            //判断回包
            if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            }else {
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"+
                        "daily response status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context:CoroutineContext,block:suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }


    //封装SharedPreferences的读写操作
    fun savePlace(place:Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}