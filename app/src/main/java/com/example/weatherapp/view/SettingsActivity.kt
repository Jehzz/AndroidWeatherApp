package com.example.weatherapp.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R

class SettingsActivity : AppCompatActivity() {

    //move to weather app. for getting the zip code from zip edit text
    private fun showZipFeature(): Boolean{
        val sharedPreferences = getSharedPreferences("WeatherZipCode", Context.MODE_PRIVATE)
        val zipCode = sharedPreferences.getString("zip_code", "n/a")
        return (zipCode.equals("n/a"))

        /*

        val sharedPreferences2 = getSharedPreferences("WeatherZipCode", Context.MODE_PRIVATE)
        val editor = sharedPreferences2.edit()
        editor.putString("zip_code", et_zip.text)
        editor.putString("units", et_units.text)

        editor.commit()

         */

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
