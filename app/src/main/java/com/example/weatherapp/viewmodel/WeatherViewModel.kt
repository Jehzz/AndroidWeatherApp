package com.example.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Network
import com.example.weatherapp.model.data.CurrentWeatherData
import com.example.weatherapp.model.data.ForecastWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This class is responsible for creating the network connection, calling Retrofit, storing the
 * returned data, and providing the information to the UI components
 * @author: Jess Osborn
 */
class WeatherViewModel : ViewModel(){

    private val currentWeatherDataSet = MutableLiveData<CurrentWeatherData>()

    fun getCurrentWeatherData(): LiveData<CurrentWeatherData> {
        return currentWeatherDataSet
    }

    private val forecastWeatherDataSet = MutableLiveData<ForecastWeatherData>()

    fun getForecastWeatherData(): LiveData<ForecastWeatherData> {
        return forecastWeatherDataSet
    }

    val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
    val key: String = "ca3efb1692ca390683b47b41ade98581"


    /**
     * Method for getting today's weather info
     * @author: Jess Osborn
     */
    fun getCurrentWeather(zip: String, units: String) {

        val network = Network(baseApiUrl)
        network.initRetrofit().getCurrentWeather(zip, key, units)
            .enqueue(object : Callback<CurrentWeatherData> {
                override fun onResponse(
                    call: Call<CurrentWeatherData>,
                    response: Response<CurrentWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    currentWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }

    /**
     * Method for getting future weather data
     * @author: Jess Osborn
     */
    fun getForecastWeather(zip: String, units: String) {

        val network = Network(baseApiUrl)
        network.initRetrofit().getForecastWeather(zip, key, units)
            .enqueue(object : Callback<ForecastWeatherData> {
                override fun onResponse(
                    call: Call<ForecastWeatherData>,
                    response: Response<ForecastWeatherData>
                ) {
                    println("success")
                    println(response.body().toString())
                    forecastWeatherDataSet.value = response.body()
                }

                override fun onFailure(call: Call<ForecastWeatherData>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }
}