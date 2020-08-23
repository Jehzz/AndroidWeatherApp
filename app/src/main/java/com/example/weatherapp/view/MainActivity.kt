package com.example.weatherapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.model.data.CurrentWeatherData
import com.example.weatherapp.model.data.ForecastWeatherData
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity is responsible for checking user input, and binding UI elements to the datasets
 * @author: Jess Osborn
 */
class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"
    private var userZip: String? = null
    private var preferredUnits: String? = null
    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel() as T
            }
        }).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnClickListeners()
        readUserPrefs()

        if (userZip.equals(null) || preferredUnits.equals(null)) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        //Trigger ViewModel to fetch weather data
        weatherViewModel.getCurrentWeather(userZip!!, preferredUnits!!)
        weatherViewModel.getForecastWeather(userZip!!, preferredUnits!!)

        createObservables()
    }

    private fun createObservables() {
        weatherViewModel.getForecastWeatherData()
            .observe(this, Observer<ForecastWeatherData> { t ->
                rv_todays_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                rv_todays_weather.adapter = CustomAdapter(t!!)

                rv_tomorrows_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                rv_tomorrows_weather.adapter = ForecastAdapter(t)

            })

        //Current Weather Card logic
        weatherViewModel.getCurrentWeatherData()
            .observe(this, Observer<CurrentWeatherData> { t ->
                tv_city_name.text = t.name
                tv_description.text = t.weather[0].main
                tv_current_temp.text = (t.main.temp) + "°"
                if ((t.main.temp.toFloat() < 60.0) && (preferredUnits.equals("Imperial"))
                    || ((t.main.temp.toFloat() < 15.6) && (preferredUnits.equals("Metric")))
                ) {
                    cv_today_weather.setCardBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.colorCool
                        )
                    )
                } else {
                    cv_today_weather.setCardBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.colorWarm
                        )
                    )
                }
            })
    }

    private fun readUserPrefs() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        userZip = sharedPreferences.getString("userZip", null)
        preferredUnits = sharedPreferences.getString("preferredUnits", null)
    }

    private fun setOnClickListeners() {
        btn_settings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }
}
