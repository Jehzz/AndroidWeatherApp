package com.example.weatherapp.model

data class PokoCurrentWeatherData(
    var name: String,
    var weather: List<weatherdata>,
    var main: main
)

data class weatherdata(
    var id: Int,
    var description: String,
    var icon: String
)