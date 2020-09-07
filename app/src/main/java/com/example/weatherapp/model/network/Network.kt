package com.example.weatherapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network class starts Retrofit
 * @author: Jess Osborn
 */
class Network(private var url: String) {
    fun initRetrofit(): RetrofitEndpoint {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitEndpoint::class.java)
    }
}