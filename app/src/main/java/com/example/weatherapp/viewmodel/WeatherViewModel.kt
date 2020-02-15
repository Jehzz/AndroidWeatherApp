package com.example.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Network
import com.example.weatherapp.model.PokoCurrentWeatherData
import com.example.weatherapp.model.PokoForecastWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel(){

    private val currentWeatherDataSet = MutableLiveData<PokoCurrentWeatherData>()

    fun getCurrentWeatherData(): LiveData<PokoCurrentWeatherData> {
        return currentWeatherDataSet
    }

    private val forecastWeatherDataSet = MutableLiveData<PokoForecastWeatherData>()

    fun getForecastWeatherData(): LiveData<PokoForecastWeatherData> {
        return forecastWeatherDataSet
    }


    fun getCurrentWeather(zip: String, units: String) {

        val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
        val key: String = "ca3efb1692ca390683b47b41ade98581"

        val network = Network(baseApiUrl)
        network.initRetrofit().getCurrentWeather(zip, key, units)
            .enqueue(object : Callback<PokoCurrentWeatherData> {
                override fun onResponse(
                    call: Call<PokoCurrentWeatherData>,
                    response: Response<PokoCurrentWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    currentWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<PokoCurrentWeatherData>, t: Throwable) {
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
            .enqueue(object : Callback<PokoForecastWeatherData> {
                override fun onResponse(
                    call: Call<PokoForecastWeatherData>,
                    response: Response<PokoForecastWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    forecastWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<PokoForecastWeatherData>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
        })
    }
}