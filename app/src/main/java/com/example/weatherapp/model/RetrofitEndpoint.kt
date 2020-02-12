package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET


interface RetrofitEndpoint {

    /*Previous example url and methods

    URL = https://api.androidhive.info/json/movies.json

    @GET("movies.json")
    fun getMovies(): Observable<List<PokoMovieData>>

    @GET("movies.json")
    fun getMovies(): Call<List<PokoMovieData>>
     */


    //Observable version
    //@GET("????")
    //fun getWeather(): Observable<List<PokoWeatherData>>

    @GET("????")
    fun getWeather(): Call<List<PokoWeatherData>>

}