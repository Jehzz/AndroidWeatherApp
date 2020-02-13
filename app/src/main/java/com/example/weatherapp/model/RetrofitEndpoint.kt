package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


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


    @GET("forecast")
    fun getWeather(@Query("zip")zipcode:String,
                   @Query("appid")apiKeyValue:String): Call<List<PokoWeatherData>>
    
    //Network.initretrofit.getweather(tvZipValue.text, resources.getString(r.string.apikey)).enqueue

}