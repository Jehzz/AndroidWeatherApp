package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitEndpoint {

    @GET("forecast")
    fun getWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String
    ): Call<List<PokoWeatherData>>

}