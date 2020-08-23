package com.example.weatherapp.model.data

/**
 * Data class for holding the current weather infomation provided by openweather
 * @author: Jess Osborn
 */
data class CurrentWeatherData(
    var name: String,
    var weather: List<weatherdata>,
    var main: main
)

data class weatherdata(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)