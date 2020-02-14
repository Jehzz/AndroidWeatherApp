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

    private val userZip: String = ""
    private val prefferedUnits: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo: Add check for zip-code and preferred units. Launch settings activity if not found

        //Create the weather viewmodel
        val weatherViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel() as T
            }
        }).get(WeatherViewModel::class.java)

        //Fetch the weather data via Retrofit through the viewmodel
        weatherViewModel.getWeather()

        //Observe the weather dataset, pass to recyclerview adapter
        weatherViewModel.getWeatherData().observe(this, Observer<PokoWeatherData> { t ->
            rv_forecast_weather.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            //Get weather data here
            tv_current_temp.text = t.list[0].main.temp
            rv_forecast_weather.adapter = CustomAdapter(t!!)
        }
        )
    }
}
