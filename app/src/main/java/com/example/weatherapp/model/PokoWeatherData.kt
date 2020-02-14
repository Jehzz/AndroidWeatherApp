package com.example.weatherapp.model

data class PokoWeatherData(
    var list: List<weatherlist>
)

data class weatherlist(
    var main: main,
    var weather: weather
)

data class main(
    var temp: String,
    var temp_min: String,
    var temp_max: String
)

data class weather(
    var day: List<Day>
)

data class Day(
    var main: String,
    var description: String,
    var icon: String
)