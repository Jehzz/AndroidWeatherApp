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

    private val currentWeatherDataSet = MutableLiveData<PokoWeatherData>()

    fun getCurrentWeatherData(): LiveData<PokoWeatherData> {
        return currentWeatherDataSet
    }

    private val forecastWeatherDataSet = MutableLiveData<PokoWeatherData>()

    fun getForecastWeatherData(): LiveData<PokoWeatherData> {
        return forecastWeatherDataSet
    }


    fun getCurrentWeather(zip: String, units: String) {

        val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
        val key: String = "ca3efb1692ca390683b47b41ade98581"

        val network = Network(baseApiUrl)
        network.initRetrofit().getCurrentWeather(zip, key, units)
            .enqueue(object : Callback<PokoWeatherData> {
                override fun onResponse(
                    call: Call<PokoWeatherData>,
                    response: Response<PokoWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    currentWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<PokoWeatherData>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }


    fun getForecastWeather(zip: String, units: String) {

        val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
        val key: String = "ca3efb1692ca390683b47b41ade98581"

        val network = Network(baseApiUrl)
        network.initRetrofit().getForecastWeather(zip, key, units)
            .enqueue(object : Callback<PokoWeatherData> {
                override fun onResponse(
                    call: Call<PokoWeatherData>,
                    response: Response<PokoWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    forecastWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<PokoWeatherData>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
        })
    }
}