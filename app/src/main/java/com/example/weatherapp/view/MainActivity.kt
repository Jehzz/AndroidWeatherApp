package com.example.weatherapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.model.PokoWeatherData
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return WeatherViewModel() as T
                }
            }
        ).get(WeatherViewModel::class.java)


        weatherViewModel.getWeatherData()
            .observe(this,
                object : Observer<List<PokoWeatherData>> {
                    override fun onChanged(t: List<PokoWeatherData>?) {
                        rv_forecast_weather.layoutManager = LinearLayoutManager(this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false)
                        rv_forecast_weather.adapter = CustomAdapter(t!!)

                        // Start the recycler view on the 5th movie
                        rv_forecast_weather.scrollToPosition(4)
                    }
                })
        weatherViewModel.getWeather()


    }
}
