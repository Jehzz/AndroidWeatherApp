package com.jessosborn.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_item_layout.*
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: WeatherViewModel

    private val PREFS_NAME = "weather prefs"
    private var userZip: String? = null
    private var preferredUnits: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout.apply {
            setOnRefreshListener { fetchWeatherFromViewModel() }
            setColorSchemeColors(getColor(R.color.blueLight))
        }
        btn_settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        readUserPrefs()
        createObservers()
        fetchWeatherFromViewModel()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        readUserPrefs()
        fetchWeatherFromViewModel()
    }

    private fun fetchWeatherFromViewModel() {
        userZip?.let {
            viewModel.fetchWeatherFromApi(
                zip = it,
                units = preferredUnits ?: "Imperial",
                key = resources.getString(R.string.api_key))
        } ?: run {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun createObservers() {
        with(viewModel) {
            currentWeatherDataSet.observe(this@MainActivity, {
                it?.let {
                    tv_city_name.text = it.name
                    tv_description.text = it.weather[0].main
                    tv_current_temp.text = getString(R.string.degrees, it.main.temp.roundToInt())
                    tv_current_humidity.text = getString(R.string.humidity, it.main.humidity)
                    tv_current_wind.text = getString(R.string.wind_speed, it.wind.speed)
                    if ((it.main.temp < 60.0) && (preferredUnits.equals("Imperial"))
                        || ((it.main.temp < 15.6) && (preferredUnits.equals("Metric")))
                    ) {
                        layout_today_weather.setBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.blueLight)
                        )
                    } else {
                        layout_today_weather.setBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.orange)
                        )
                    }
                }
            })
            forecastWeatherDataSet.observe(this@MainActivity, { forecastWeatherData ->
                forecastWeatherData?.let { it ->
                    rv_todays_weather.adapter = ForecastAdapter(it.list.take(8))
                    rv_tomorrows_weather.adapter = ForecastAdapter(it.list.drop(8).take(8))
                }
            })
            isNetworkLoading.observe(this@MainActivity, {
                swipeRefreshLayout.isRefreshing = it
            })
            networkError.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun readUserPrefs() {
        userZip = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("userZip", null)
        preferredUnits =
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("preferredUnits", null)
    }
}