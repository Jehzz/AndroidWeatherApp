package com.example.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Network
import com.example.weatherapp.model.PokoWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel(){

    private val dataSet =
        MutableLiveData<PokoWeatherData>()

    fun getWeatherData(): LiveData<PokoWeatherData> {
        return dataSet
    }

    fun getWeather(){


        //test values
        val fullSampleUrl: String = "https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b6907d289e10d714a6e88b30761fae22"

        //tested, valid api call with my key
        val testUrl: String = "https://api.openweathermap.org/data/2.5/forecast?id=524901&appid=ca3efb1692ca390683b47b41ade98581"


        val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
        val apiZip: String = "zip=30339,us"
        val apiKey: String = "appid=ca3efb1692ca390683b47b41ade98581"
        val zip: String = "30339,us"
        val key: String = "ca3efb1692ca390683b47b41ade98581"


        val network = Network(baseApiUrl)
        network.initRetrofit().getWeather(zip, key)
            .enqueue(object : Callback<PokoWeatherData> {
                override fun onResponse(
                    call: Call<PokoWeatherData>,
                    response: Response<PokoWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    dataSet.value = response.body()
                }

                override fun onFailure(call: Call<PokoWeatherData>, t: Throwable) {
                    t.printStackTrace()
                println("failure")
            }
        })
    }
}