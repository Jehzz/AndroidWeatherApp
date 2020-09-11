package com.example.weatherapp.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.network.Network
import com.example.weatherapp.utils.baseApiUrl
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    private val TAG = "WeatherRepository"

    //TODO: Inject Network dependency
    private val network = Network(baseApiUrl)

    private val repoJob = SupervisorJob()
    private val repoScope = CoroutineScope(Dispatchers.IO + repoJob)

    private val currentWeatherData = MutableLiveData<CurrentWeatherData>()
    private val forecastWeatherData = MutableLiveData<ForecastWeatherData>()
    private val isNetworkLoading = MutableLiveData<Boolean>()

    fun getCurrentWeatherData(): LiveData<CurrentWeatherData> = currentWeatherData
    fun getForecastWeatherData(): LiveData<ForecastWeatherData> = forecastWeatherData
    fun getIsNetworkLoading(): LiveData<Boolean> = isNetworkLoading

    fun fetchWeatherFromApi(zip: String, units: String, key: String) {
        isNetworkLoading.value = true

        repoScope.launch {
            withContext(this.coroutineContext) {
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
            withContext(this.coroutineContext) {
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