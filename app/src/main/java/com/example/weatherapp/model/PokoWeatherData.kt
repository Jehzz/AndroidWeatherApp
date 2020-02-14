package com.example.weatherapp.model

data class PokoWeatherData(
    var list: List<weatherlist>
)

data class weatherlist(
    var main: main,
    var weather: List<weather>,
    var dt_txt: String
)

data class main(
    var temp: String,
    var temp_min: String,
    var temp_max: String
)

data class weather(
    var main: String,
    var description: String,
    var icon: String
)
