package com.example.weatherapp.model.network

import com.example.weatherapp.model.repository.CurrentWeatherData
import com.example.weatherapp.model.repository.ForecastWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit creates the correct API call and maps the returned data to the Poko classes
 */
interface RetrofitEndpoint {

    @GET("forecast")
    fun getForecastWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<ForecastWeatherData>

    @GET("weather")
    fun getCurrentWeather(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<CurrentWeatherData>

}