package com.example.weatherapp.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val userZip: String = ""
    private val preferredUnits: String = ""


    private fun saveInputs() {
        val sharedPreferences = getSharedPreferences(userZip, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(userZip, et_zip.text.toString())
        editor.putString(preferredUnits, et_units.text.toString())
        Toast.makeText(this@SettingsActivity, "values saved", Toast.LENGTH_SHORT).show()
        editor.apply()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Listen for Save button, save zip and unit to sharedpreferences
        val btnSave: Button = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            saveInputs()
        }
    }
}
