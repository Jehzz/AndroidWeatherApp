package com.example.simpleweather.model.repository

data class ForecastWeatherData(
    var list: List<weatherlist>,
)

data class CurrentWeatherData(
    var name: String,
    var weather: List<weatherdata>,
    var main: main,
)

data class weatherlist(
    var main: main,
    var weather: List<weatherdata>,
    var dt_txt: String,
)

data class main(
    var temp: String,
    var temp_min: String,
    var temp_max: String,
)

data class weatherdata(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String,
)
