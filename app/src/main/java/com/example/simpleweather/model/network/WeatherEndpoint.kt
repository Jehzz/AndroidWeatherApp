package com.example.simpleweather.model.network

import com.example.simpleweather.model.repository.CurrentWeatherData
import com.example.simpleweather.model.repository.ForecastWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherEndpoint {

    @GET("weather")
    fun getCurrentWeather(
        @Query("zip") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
    ): Call<CurrentWeatherData>

    @GET("forecast")
    fun getForecastWeather(
        @Query("zip") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
    ): Call<ForecastWeatherData>

    companion object {
        const val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
    }
}