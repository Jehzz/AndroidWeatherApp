package com.jessosborn.simpleweather.model.repository

data class CurrentWeatherData(
    var name: String,
    var main: Main,
    var weather: List<WeatherData>,
    var wind: Wind,
)

data class ForecastWeatherData(
    var list: List<WeatherList>,
)

data class WeatherList(
    var dt: String,
    var dt_txt: String,
    var main: Main,
    var weather: List<WeatherData>,
)

data class Main(
    var temp: Float,
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

data class Wind(
    var speed: String,
    var deg: String,
)
