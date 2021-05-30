package com.jessosborn.simpleweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jessosborn.simpleweather.model.repository.CurrentWeatherData
import com.jessosborn.simpleweather.model.repository.ForecastWeatherData
import com.jessosborn.simpleweather.model.repository.WeatherRepository
import com.jessosborn.simpleweather.utils.getCountryFromZip
import javax.inject.Inject

class WeatherViewModel @Inject constructor() : ViewModel() {

    //TODO: Inject Repo dependency
    private val weatherRepo = WeatherRepository()

    val currentWeatherDataSet: LiveData<CurrentWeatherData> = weatherRepo.currentWeatherData
    val forecastWeatherDataSet: LiveData<ForecastWeatherData> = weatherRepo.forecastWeatherData
    val isNetworkLoading: LiveData<Boolean> = weatherRepo.isNetworkLoading
    val networkError: LiveData<String> = weatherRepo.errorMessage

    fun fetchWeatherFromApi(zip: String, units: String, key: String) {
        val country = getCountryFromZip(zip)
        weatherRepo.fetchWeatherFromApi(zip, country, units, key)
    }
}