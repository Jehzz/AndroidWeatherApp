package com.example.weatherapp.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.network.Network
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    private val TAG = "WeatherRepository"

    private val currentWeatherData = MutableLiveData<CurrentWeatherData>()
    fun getCurrentWeatherData(): LiveData<CurrentWeatherData> = currentWeatherData

    private val forecastWeatherData = MutableLiveData<ForecastWeatherData>()
    fun getForecastWeatherData(): LiveData<ForecastWeatherData> = forecastWeatherData

    private val isNetworkLoading = MutableLiveData<Boolean>()
    fun getIsNetworkLoading(): LiveData<Boolean> = isNetworkLoading

    private val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
    private val key: String = "ca3efb1692ca390683b47b41ade98581"
    private val network = Network(baseApiUrl)   //TODO: Inject Network dependency

    private val repoJob = SupervisorJob()
    private val repoScope = CoroutineScope(Dispatchers.Main + repoJob)

    fun fetchWeatherFromApi(zip: String, units: String) {

        isNetworkLoading.value = true

        repoScope.launch {
            withContext(Dispatchers.IO) {
                network.initRetrofit().getCurrentWeather(zip, key, units)
                    .enqueue(object : Callback<CurrentWeatherData> {
                        override fun onResponse(
                            call: Call<CurrentWeatherData>,
                            response: Response<CurrentWeatherData>,
                        ) {
                            Log.d(TAG, "onResponse: Current Weather: " + response.body().toString())
                            currentWeatherData.value = response.body()
                        }

                        override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                            Log.d(TAG, "onFailure: Current Weather: " + t.printStackTrace())
                        }
                    })
            }

            withContext(Dispatchers.IO) {
                network.initRetrofit().getForecastWeather(zip, key, units)
                    .enqueue(object : Callback<ForecastWeatherData> {
                        override fun onResponse(
                            call: Call<ForecastWeatherData>,
                            response: Response<ForecastWeatherData>,
                        ) {
                            Log.d(TAG, "onResponse: Forecast Weather " + response.body().toString())
                            forecastWeatherData.value = response.body()
                            isNetworkLoading.value = false
                        }

                        override fun onFailure(call: Call<ForecastWeatherData>, t: Throwable) {
                            Log.d(TAG, "onFailure: Forecast Weather: " + t.printStackTrace())
                            isNetworkLoading.value = false
                        }
                    })
            }
        }
    }
}