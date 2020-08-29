package com.example.weatherapp.model

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //RxJava currently unused, may utilize in Repository layer
            .build()

        return retrofit.create(RetrofitEndpoint::class.java)
    }
}