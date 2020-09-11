package com.example.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.utils.isValidZip
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * This activity takes the user's location as a zipcode, and their preferred units of measure.
 * @author: Jess Osborn
 */
class SettingsActivity : AppCompatActivity() {

    private val TAG = "SettingsActivity"
    private val PREFS_NAME = "weather prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        readUserPrefs()

        setOnClickListeners()

        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_units.adapter = adapter
        }
    }

    private fun setOnClickListeners() {
        btn_save.setOnClickListener {
            if (isValidZip(et_zip.text.toString())) {
                saveInputs()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                et_zip.error = "Enter a proper Zip Code"
            }
        }
    }

    private fun saveInputs() {
        Log.d(TAG, "saveInputs: ")
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userZip", et_zip.text.toString()).apply()
        editor.putString("preferredUnits", spinner_units.selectedItem.toString()).apply()
        editor.commit()
    }

    private fun readUserPrefs() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        et_zip.setText(sharedPreferences.getString("userZip", null))
    }
}
