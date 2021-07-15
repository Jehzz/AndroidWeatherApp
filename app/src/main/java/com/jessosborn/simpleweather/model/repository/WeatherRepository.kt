package com.jessosborn.simpleweather.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jessosborn.simpleweather.App.Companion.context
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.model.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val network: Network
) {

    private val _currentWeatherData = MutableLiveData<CurrentWeatherData>()
    private val _forecastWeatherData = MutableLiveData<ForecastWeatherData>()
    private val _isNetworkLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val currentWeatherData: LiveData<CurrentWeatherData> get() = _currentWeatherData
    val forecastWeatherData: LiveData<ForecastWeatherData> get() = _forecastWeatherData
    val isNetworkLoading: LiveData<Boolean> get() = _isNetworkLoading
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchWeatherFromApi(zip: String, country: String, units: String, key: String) {
        _isNetworkLoading.value = true
        CoroutineScope(IO).launch {
            withContext(this.coroutineContext) {
                network.initRetrofit().getCurrentWeather("$zip,$country", key, units)
                    .enqueue(object : Callback<CurrentWeatherData> {
                        override fun onResponse(
                            call: Call<CurrentWeatherData>,
                            response: Response<CurrentWeatherData>,
                        ) {
                            when (response.code()) {
                                200 -> _currentWeatherData.value = response.body()
                                401 ->
                                    _errorMessage.value =
                                        context.getString(R.string.error_api_key)
                                404 ->
                                    _errorMessage.value =
                                        context.getString(R.string.error_city_not_found)
                                else -> _errorMessage.value = response.message()
                            }
                        }
                        override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                            _errorMessage.value =
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
                                200 -> _forecastWeatherData.value = response.body()
                                401 ->
                                    _errorMessage.value =
                                        context.getString(R.string.error_api_key)
                                404 ->
                                    _errorMessage.value =
                                        context.getString(R.string.error_city_not_found)
                                else -> _errorMessage.value = response.message()
                            }
                            _isNetworkLoading.value = false
                        }
                        override fun onFailure(call: Call<ForecastWeatherData>, t: Throwable) {
                            _isNetworkLoading.value = false
                            _errorMessage.value =
                                context.getString(R.string.error_weather_service_unavailable)
                        }
                    })
            }
        }
    }
}
