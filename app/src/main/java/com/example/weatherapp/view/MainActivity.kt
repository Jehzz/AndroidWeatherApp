package com.example.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity is responsible for checking user input, and binding UI elements to the datasets
 * @author: Jess Osborn
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
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
        Log.d(TAG, "onCreate: ")

        setOnClickListeners()
        readUserPrefs()

        if (userZip.equals(null) || preferredUnits.equals(null)) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        createObservers()
        weatherViewModel.fetchWeatherFromApi(userZip!!, preferredUnits!!)
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

        weatherViewModel.getForecastWeatherData()
            .observe(this, { t ->
                rv_todays_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                rv_todays_weather.adapter = ForecastAdapter(t.list.take(8))
                rv_tomorrows_weather.layoutManager = GridLayoutManager(this@MainActivity, 4)
                rv_tomorrows_weather.adapter = ForecastAdapter(t.list.drop(8))
            })

        //Current Weather Card logic
        weatherViewModel.getCurrentWeatherData()
            .observe(this, { t ->
                tv_city_name.text = t.name
                tv_description.text = t.weather[0].main
                tv_current_temp.text = t.main.temp + "°"
                if ((t.main.temp.toFloat() < 60.0) && (preferredUnits.equals("Imperial"))
                    || ((t.main.temp.toFloat() < 15.6) && (preferredUnits.equals("Metric")))
                ) {
                    cv_today_weather.setCardBackgroundColor(
                        ContextCompat.getColor(applicationContext, R.color.colorCool)
                    )
                } else {
                    cv_today_weather.setCardBackgroundColor(
                        ContextCompat.getColor(applicationContext, R.color.colorWarm)
                    )
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
