package com.example.simpleweather.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleweather.App.Companion.context
import com.example.simpleweather.R
import com.example.simpleweather.model.network.Network
import com.example.simpleweather.utils.isUsZip
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    //TODO: Inject Network dependency
    private val network = Network()

    private val repoJob = SupervisorJob()
    private val repoScope = CoroutineScope(Dispatchers.IO + repoJob)

    private val currentWeatherData = MutableLiveData<CurrentWeatherData>()
    private val forecastWeatherData = MutableLiveData<ForecastWeatherData>()
    private val isNetworkLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    fun getCurrentWeatherData(): LiveData<CurrentWeatherData> = currentWeatherData
    fun getForecastWeatherData(): LiveData<ForecastWeatherData> = forecastWeatherData
    fun getIsNetworkLoading(): LiveData<Boolean> = isNetworkLoading
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun fetchWeatherFromApi(zip: String, units: String, key: String) {
        isNetworkLoading.value = true
        val country = if (isUsZip(zip)) "us" else "ca"

        repoScope.launch {
            withContext(this.coroutineContext) {
                network.initRetrofit().getCurrentWeather("$zip,$country", key, units)
                    .enqueue(object : Callback<CurrentWeatherData> {
                        override fun onResponse(
                            call: Call<CurrentWeatherData>,
                            response: Response<CurrentWeatherData>,
                        ) {
                            when (response.code()) {
                                200 -> currentWeatherData.value = response.body()
                                404 -> errorMessage.value =
                                    context.getString(R.string.error_city_not_found)
                                else -> errorMessage.value = context.getString(R.string.error_error)
                            }
                        }
                        override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                            errorMessage.value =
                                context.getString(R.string.error_weather_service_unavailable)
                        }
                    })
            }
            withContext(this.coroutineContext) {
                network.initRetrofit().getForecastWeather("$zip,$country", key, units)
                    .enqueue(object : Callback<ForecastWeatherData> {
                        override fun onResponse(
                            call: Call<ForecastWeatherData>,
                            response: Response<ForecastWeatherData>,
                        ) {
                            when (response.code()) {
                                200 -> forecastWeatherData.value = response.body()
                                404 -> errorMessage.value =
                                    context.getString(R.string.error_city_not_found)
                                else -> errorMessage.value = context.getString(R.string.error_error)
                            }
                            isNetworkLoading.value = false
                        }
                        override fun onFailure(call: Call<ForecastWeatherData>, t: Throwable) {
                            isNetworkLoading.value = false
                        }
                    })
            }
        }
    }
}