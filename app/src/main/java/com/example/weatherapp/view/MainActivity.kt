package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.model.PokoCurrentWeatherData
import com.example.weatherapp.model.PokoForecastWeatherData
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"
    private var userZip: String? = null
    private var preferredUnits: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Settings button
        btn_settings.setOnClickListener(View.OnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        })

        //Fetch zip and units from shared preferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        userZip = sharedPreferences.getString("userZip", null)
        preferredUnits = sharedPreferences.getString("preferredUnits", null)

        //check for zip-code and preferred units. Launch settings activity if not found
        if (userZip.equals(null) || preferredUnits.equals(null)) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        } else {//Create the weather viewmodel
            val weatherViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return WeatherViewModel() as T
                }
            }).get(WeatherViewModel::class.java)

            //Fetch the current and forecast weather data
            weatherViewModel.getCurrentWeather(userZip!!, preferredUnits!!)
            weatherViewModel.getForecastWeather(userZip!!, preferredUnits!!)

            //Observe the forecastweather dataset, pass to recyclerview adapter
            weatherViewModel.getForecastWeatherData()
                .observe(this, Observer<PokoForecastWeatherData> { t ->
                    rv_todays_weather.layoutManager = GridLayoutManager(
                    this@MainActivity,
                        4
                )
                //Get and assign weather data to views here!
                rv_todays_weather.adapter = CustomAdapter(t!!)
            }
            )
            weatherViewModel.getCurrentWeatherData()
                .observe(this, Observer<PokoCurrentWeatherData> { t ->

                    //Get data and assign to views here
                    tv_city_name.text = t.name
                    tv_description.text = t.weather[0].main
                    tv_current_temp.text = (t.main.temp) + "°"

                    //Check temperature and assign warm or cool color values
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
        } //end else
    }
}
