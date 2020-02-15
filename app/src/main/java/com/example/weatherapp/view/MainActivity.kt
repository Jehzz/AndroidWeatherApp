package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

            //Fetch the weather data via Retrofit through the viewmodel
            weatherViewModel.getForecastWeather(userZip!!, preferredUnits!!)

            //Observe the weather dataset, pass to recyclerview adapter
            weatherViewModel.getForecastWeatherData().observe(this, Observer<PokoWeatherData> { t ->
                rv_todays_weather.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                //Get and assign weather data to views here!
                tv_current_temp.text = t.list[0].main.temp
                rv_todays_weather.adapter = CustomAdapter(t!!)
            }
            )
        } //end else
    }
}
