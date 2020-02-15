package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Save Button listener. Save data and return to MainActivity
        val btnSave: Button = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            //Check validity of zipcode
            if (isValidZip(et_zip.text.toString())) {
                saveInputs()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                et_zip.error = "Enter a proper Zip Code"
            }
        }

        //Populate units spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_units.adapter = adapter
        }
    }

    //Save inputs to shared preferences
    private fun saveInputs() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userZip", et_zip.text.toString()).apply()
        editor.putString("preferredUnits", spinner_units.selectedItem.toString()).apply()
        editor.commit()
    }

    //Check input
    private fun isValidZip(text: String): Boolean {
        var pattern = Regex("^[0-9]{5}")            //Zipcode pattern is 5 digits
        return pattern.matches(text)                        //return true if input matches pattern
    }
}
