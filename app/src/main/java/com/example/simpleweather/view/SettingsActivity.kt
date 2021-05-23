package com.example.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleweather.R
import com.example.simpleweather.utils.isValidZip
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        readUserPrefs()
        setOnClickListeners()
        populateUnitsSpinner()
    }

    private fun setOnClickListeners() {
        btn_save.setOnClickListener {
            if (isValidZip(et_zip.text.toString())) {
                saveInputs()
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                et_zip.error = getString(R.string.error_enter_valid_zip)
            }
        }
    }

    private fun populateUnitsSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice)
            spinner_units.adapter = adapter
        }
    }

    private fun saveInputs() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putString("userZip", et_zip.text.toString())
            .putString("preferredUnits", spinner_units.selectedItem.toString())
            .apply()
    }

    private fun readUserPrefs() {
        // TODO: set Units menu to user's preference
        et_zip.setText(getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("userZip", null))
    }
}
