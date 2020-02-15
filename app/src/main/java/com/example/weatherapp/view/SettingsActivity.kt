package com.example.weatherapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"

    //Save inputs to shared preferences
    private fun saveInputs() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userZip", et_zip.text.toString()).apply()
        editor.putString("preferredUnits", et_units.text.toString()).apply()
        editor.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Save Button listener
        val btnSave: Button = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            saveInputs()                                //save data
            val returnIntent: Intent =
                Intent(this, MainActivity::class.java) //return to main activity
            startActivity(returnIntent)
        }
    }
}
