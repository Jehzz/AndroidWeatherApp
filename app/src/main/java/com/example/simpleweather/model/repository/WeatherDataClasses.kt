package com.example.simpleweather.model.repository

data class ForecastWeatherData(
    var list: List<WeatherList>,
)

data class CurrentWeatherData(
    var name: String,
    var main: Main,
    var weather: List<WeatherData>,
)

data class WeatherList(
    var dt: String,
    var main: Main,
    var weather: List<WeatherData>,
    var dt_txt: String,
)

data class Main(
    var temp: String,
    var temp_min: String,
    var temp_max: String,
    var humidity: String,
)

data class WeatherData(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String,
)
