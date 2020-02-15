package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitEndpoint {

    @GET("forecast")
    fun getForecastWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<PokoForecastWeatherData>

    @GET("weather")
    fun getCurrentWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<PokoCurrentWeatherData>

}