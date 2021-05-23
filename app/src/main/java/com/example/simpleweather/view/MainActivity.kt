package com.example.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleweather.R
import com.example.simpleweather.viewmodel.WeatherViewModel
import com.example.simpleweather.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"

    private var userZip: String? = null
    private var preferredUnits: String? = null

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, WeatherViewModelFactory())
            .get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_todays_weather.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_tomorrows_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)

        swipeRefreshLayout.setOnRefreshListener {
            fetchWeatherFromViewModel()
        }

        btn_settings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }

        readUserPrefs()
        createObservers()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        readUserPrefs()
        userZip?.let {
            weatherViewModel.fetchWeatherFromApi(it,
                preferredUnits ?: "Imperial",
                resources.getString(R.string.api_key))
        }
    }

    private fun fetchWeatherFromViewModel() {
        userZip?.let {
            weatherViewModel.fetchWeatherFromApi(it,
                preferredUnits ?: "Imperial",
                resources.getString(R.string.api_key))
        } ?: run {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun createObservers() {
        weatherViewModel.forecastWeatherDataSet
            .observe(this, { forecastWeatherData ->
                forecastWeatherData?.let { it ->
                    rv_todays_weather.adapter = ForecastAdapter(it.list.take(8))
                    rv_tomorrows_weather.adapter = ForecastAdapter(it.list.drop(8).take(8))
                }
            })

        weatherViewModel.currentWeatherDataSet
            .observe(this, {
                it?.let {
                    tv_city_name.text = it.name
                    tv_description.text = it.weather[0].main
                    tv_current_temp.text = "${it.main.temp}°"
                    if ((it.main.temp.toFloat() < 60.0) && (preferredUnits.equals("Imperial"))
                        || ((it.main.temp.toFloat() < 15.6) && (preferredUnits.equals("Metric")))
                    ) {
                        layout_today_weather.setBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.colorCool)
                        )
                    } else {
                        layout_today_weather.setBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.colorWarm)
                        )
                    }
                }
            })

        weatherViewModel.isNetworkLoading
            .observe(this, {
                when (it) {
                    true -> swipeRefreshLayout.isRefreshing = true
                    false -> swipeRefreshLayout.isRefreshing = false
                }
            })

        weatherViewModel.networkError
            .observe(this, {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            })
    }

    private fun readUserPrefs() {
        userZip = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("userZip", null)
        preferredUnits =
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("preferredUnits", null)
    }
}
