package com.example.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Network
import com.example.weatherapp.model.PokoWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO: add cityID parameter to constructor, then append to apiURL?
class WeatherViewModel : ViewModel(){


    private val dataSet =
        MutableLiveData<List<PokoWeatherData>>()

    fun getWeatherData(): LiveData<List<PokoWeatherData>> {
        return dataSet
    }

    fun getWeather(){
        val apiUrl: String = "api.openweathermap.org/data/2.5/forecast?"
        val apiCity: String = "id=Atlanta"
        val apiZip: String = "zip=30339,us"
        val apiKey: String = "&appid=ca3efb1692ca390683b47b41ade98581"

        //TODO: append cityID and api key
        val network = Network(apiUrl+apiCity+apiKey)

        network.initRetrofit().getWeather(apiUrl, apiKey).enqueue(object : Callback<List<PokoWeatherData>>{
            override fun onFailure(call: Call<List<PokoWeatherData>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                println("failure")
            }

            override fun onResponse(

                call: Call<List<PokoWeatherData>>,
                response: Response<List<PokoWeatherData>>) {
                dataSet.value = response.body()
                println("success")
                println(dataSet)
            }

        })
    }
}