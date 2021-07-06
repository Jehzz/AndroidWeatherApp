package com.jessosborn.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.databinding.ActivityMainBinding
import com.jessosborn.simpleweather.utils.DataStoreUtil
import com.jessosborn.simpleweather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: WeatherViewModel

    private lateinit var binding: ActivityMainBinding

    private var preferredUnits: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            swipeRefreshLayout.apply {
                setOnRefreshListener { refresh() }
                setColorSchemeColors(getColor(R.color.blueLight))
            }
            todaysWeather.btnSettings.setOnClickListener { navigateToSettings() }
        }
        setContentView(binding.root)
        createObservers()
        refresh()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        refresh()
    }

    private fun refresh() {
        CoroutineScope(Dispatchers.Main).launch {
            DataStoreUtil.getString(applicationContext, DataStoreUtil.USER_ZIP)?.let {
                viewModel.fetchWeatherFromApi(
                    zip = it,
                    units = preferredUnits ?: "Imperial",
                    key = resources.getString(R.string.api_key))
            } ?: run {
                navigateToSettings()
            }
        }
    }

    private fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun createObservers() {
        with(viewModel) {
            currentWeatherDataSet.observe(this@MainActivity, {
                it?.let {
                    binding.todaysWeather.apply {
                        tvCityName.text = it.name
                        tvDescription.text = it.weather[0].main
                        tvCurrentTemp.text = getString(R.string.degrees, it.main.temp.roundToInt())
                        tvCurrentHumidity.text = getString(R.string.humidity, it.main.humidity)
                        tvCurrentWind.text = getString(R.string.wind_speed, it.wind.speed)

                        if ((it.main.temp < 60.0) && (preferredUnits.equals("Imperial"))
                            || ((it.main.temp < 15.6) && (preferredUnits.equals("Metric")))
                        ) {
                            root.setBackgroundColor(
                                ContextCompat.getColor(applicationContext, R.color.blueLight)
                            )
                        } else {
                            root.setBackgroundColor(
                                ContextCompat.getColor(applicationContext, R.color.orange)
                            )
                        }
                    }
                }
            })
            forecastWeatherDataSet.observe(this@MainActivity, { forecastWeatherData ->
                forecastWeatherData?.let { it ->
                    binding.todaysForecast.rvTodaysWeather.adapter =
                        ForecastAdapter(it.list.take(8))
                    binding.tomorrowForecast.rvTomorrowsWeather.adapter =
                        ForecastAdapter(it.list.drop(8).take(8))
                }
            })
            isNetworkLoading.observe(this@MainActivity, {
                binding.swipeRefreshLayout.isRefreshing = it
            })
            networkError.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            })
        }
    }
}