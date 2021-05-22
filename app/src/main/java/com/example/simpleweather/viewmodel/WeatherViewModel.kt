package com.example.simpleweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleweather.model.repository.CurrentWeatherData
import com.example.simpleweather.model.repository.ForecastWeatherData
import com.example.simpleweather.model.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    //TODO: Inject Repo dependency
    private val weatherRepo = WeatherRepository()

    val currentWeatherDataSet: LiveData<CurrentWeatherData> = weatherRepo.getCurrentWeatherData()
    val forecastWeatherDataSet: LiveData<ForecastWeatherData> = weatherRepo.getForecastWeatherData()
    val isNetworkLoading: LiveData<Boolean> = weatherRepo.getIsNetworkLoading()
    val networkError: LiveData<String> = weatherRepo.getErrorMessage()

    fun fetchWeatherFromApi(zip: String, units: String, key: String) {
        weatherRepo.fetchWeatherFromApi(zip, units, key)
    }
}

class WeatherViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel() as T
    }
}