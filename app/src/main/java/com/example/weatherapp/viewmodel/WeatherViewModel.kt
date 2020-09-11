package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.repository.CurrentWeatherData
import com.example.weatherapp.model.repository.ForecastWeatherData
import com.example.weatherapp.model.repository.WeatherRepository

/**
 * This class is responsible for creating the network connection, calling Retrofit, storing the
 * returned data, and providing the information to the UI components
 * @author: Jess Osborn
 */
class WeatherViewModel : ViewModel() {

    private val TAG = "WeatherViewModel"

    //TODO: Inject Repo dependency
    private val weatherRepo = WeatherRepository()

    val currentWeatherDataSet: LiveData<CurrentWeatherData>
    val forecastWeatherDataSet: LiveData<ForecastWeatherData>
    val isNetworkLoading: LiveData<Boolean>

    init {
        currentWeatherDataSet = weatherRepo.getCurrentWeatherData()
        forecastWeatherDataSet = weatherRepo.getForecastWeatherData()
        isNetworkLoading = weatherRepo.getIsNetworkLoading()
    }

    fun fetchWeatherFromApi(zip: String, units: String, key: String) {
        weatherRepo.fetchWeatherFromApi(zip, units, key)
    }
}

class WeatherViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel() as T
    }
}