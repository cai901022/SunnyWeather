package com.sunnyweather.sunnyweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.sunnyweather.R
import com.sunnyweather.sunnyweather.ui.weather.WeatherActivity

/*************************************************************
 * @ProjectName:  SunnyWeather
 * @Desc:
 * @Author:  Caixiaojia
 * @Date:  2022/5/13
 **************************************************************/
class PlaceFragment : Fragment(){

    //laze懒加载获取PlaceViewModel的实例
    val viewModel by lazy{ ViewModelProvider(this).get(PlaceViewModel::class.java)}

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)  //加载fragment_place布局
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //对PlaceFragment进行判断，如果当前已有存储的城市数据，那么就获取已存储的数据并解析成Place对象，
        // 然后使用它的经纬度坐标和城市名直接跳转并传递给WeatherActivity
        if(viewModel.isPlaceSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
        }

        //标准写法设置设配器数据
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = layoutManager                //给recyclerView设置LayoutManager和适配器
        adapter = PlaceAdapter(this,viewModel.placeList)  //传入数据模型适配
        recyclerView?.adapter = adapter

        //监听搜索框内容的变化情况(搜索城市数据请求发起)
        val searchPlaceEdit = view?.findViewById<EditText>(R.id.searchPlaceEdit)
        val bgImageView = view?.findViewById<ImageView>(R.id.bgImageView)
        searchPlaceEdit?.addTextChangedListener { editable->
            val content = editable.toString()
            Log.d("PlaceFragment",content)
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)//发起搜索城市数据的网络请求
            } else {
                //隐藏RecyclerView，仅显示背景图
                recyclerView?.visibility = View.GONE   //隐藏
                bgImageView?.visibility = View.VISIBLE //显示
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged() //重绘当前可见区域
            }
        }

        //获取服务器响应的数据
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
            val places = result.getOrNull()
            if(places != null)
            {
                Log.d("PlaceFragment","places=$places")
                recyclerView?.visibility = View.VISIBLE
                bgImageView?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()//将异常原因打印出来
            }
        })
    }

}