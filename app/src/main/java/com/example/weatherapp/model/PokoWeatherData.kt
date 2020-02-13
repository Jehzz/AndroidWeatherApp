package com.example.weatherapp.model

data class PokoWeatherData (
    val list: List<weatherlist>
)

data class weatherlist(
    val main: main,
    val weather: weather
)

data class main(
    val temp: String,
    val temp_min: String,
    val temp_max: String
)

data class weather(
    val day: List<Day>
)

data class Day(
    val main: String,
    val description: String,
    val icon: String
)