package com.example.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
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
        Log.d(TAG, "onCreate: ")

        setOnClickListeners()
        readUserPrefs()
        createObservers()

        userZip?.replace(" ", "") ?: let {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        readUserPrefs()
        weatherViewModel.fetchWeatherFromApi(userZip!!, preferredUnits!!)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    private fun createObservers() {
        Log.d(TAG, "createObservers: ")

        weatherViewModel.forecastWeatherDataSet
            .observe(this, {
                it?.let {//Let block to avoid NPE on weatherdata when user enters valid but non-existent zipcode
                    rv_todays_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                    rv_todays_weather.adapter = ForecastAdapter(it.list.take(8))
                    rv_tomorrows_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                    rv_tomorrows_weather.adapter = ForecastAdapter(it.list.drop(8))
                }
            })
        weatherViewModel.currentWeatherDataSet
            .observe(this, {
                it?.let {
                    tv_city_name.text = it.name
                    tv_description.text = it.weather[0].main
                    tv_current_temp.text = it.main.temp + "°"
                    if ((it.main.temp.toFloat() < 60.0) && (preferredUnits.equals("Imperial"))
                        || ((it.main.temp.toFloat() < 15.6) && (preferredUnits.equals("Metric")))
                    ) {
                        cv_today_weather.setCardBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.colorCool)
                        )
                    } else {
                        cv_today_weather.setCardBackgroundColor(
                            ContextCompat.getColor(applicationContext, R.color.colorWarm)
                        )
                    }
                }
            })
        weatherViewModel.isNetworkLoading
            .observe(this, {
                when (it) {
                    true -> progressBar.visibility = View.VISIBLE
                    false -> progressBar.visibility = View.GONE
                }
            })
    }

    private fun readUserPrefs() {
        Log.d(TAG, "readUserPrefs: ")
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        userZip = sharedPreferences.getString("userZip", null)
        preferredUnits = sharedPreferences.getString("preferredUnits", null)
    }

    private fun setOnClickListeners() {
        Log.d(TAG, "setOnClickListeners: ")
        btn_settings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
    }
}
