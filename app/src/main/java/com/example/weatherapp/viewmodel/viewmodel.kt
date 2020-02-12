package com.example.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Network
import com.example.weatherapp.model.PokoWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel(){

    private val dataSet =
        MutableLiveData<List<PokoWeatherData>>()

    fun getMovieList(): LiveData<List<PokoWeatherData>> {
        return dataSet
    }

    fun getWeather(){

        //val network = Network("https://api.androidhive.info/json/movies.json") example from previous project
        //TODO enter correct weather api url
        val network = Network("WEATHERAPI")

        network.initRetrofit().getWeather().enqueue(object : Callback<List<PokoWeatherData>>{
            override fun onFailure(call: Call<List<PokoWeatherData>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<List<PokoWeatherData>>,
                response: Response<List<PokoWeatherData>>) {
                dataSet.value = response.body()
            }

        })
    }
}