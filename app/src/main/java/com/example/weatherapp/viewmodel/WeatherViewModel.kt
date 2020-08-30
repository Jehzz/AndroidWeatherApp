package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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

    private val weatherRepo = WeatherRepository()

    val currentWeatherDataSet: LiveData<CurrentWeatherData>
    val forecastWeatherDataSet: LiveData<ForecastWeatherData>

    init {
        currentWeatherDataSet = weatherRepo.getCurrentWeatherData()
        forecastWeatherDataSet = weatherRepo.getForecastWeatherData()
    }

    fun fetchWeatherFromApi(zip: String, units: String) {
        weatherRepo.fetchWeatherFromApi(zip, units)
    }
}