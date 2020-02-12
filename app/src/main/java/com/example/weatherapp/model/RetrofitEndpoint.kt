package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET


interface RetrofitEndpoint {

    //Previous example
    //https://api.androidhive.info/json/movies.json
    //@GET("movies.json")
    //fun getMovies(): Observable<List<PokoMovieData>>

    //@GET("movies.json")
    //fun getMovies(): Call<List<PokoMovieData>>




    //Current Observable version
    //@GET("????")  figure out how to set this value to zip code
    //fun getWeather(): Observable<List<PokoWeatherData>>


    @GET("????")
    fun getWeather(): Call<List<PokoWeatherData>>

}