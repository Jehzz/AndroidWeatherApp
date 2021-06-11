package com.jessosborn.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.databinding.ActivitySettingsBinding
import com.jessosborn.simpleweather.utils.isValidZip

class SettingsActivity : AppCompatActivity() {

    private val PREFS_NAME = "weather prefs"
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populateUnitsSpinner()

        binding.apply {
            etZip.setText(
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString("userZip", null)
            )
            btnSave.setOnClickListener {
                if (etZip.text.toString().isValidZip()) {
                    saveInputs()
                    startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    etZip.error = getString(R.string.error_enter_valid_zip)
                }
            }
        }
    }

    private fun populateUnitsSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice)
            binding.apply {
                spinnerUnits.adapter = adapter
                spinnerUnits.setSelection(adapter.getPosition(
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .getString("preferredUnits", null)
                ))
            }
        }
    }

    private fun saveInputs() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putString("userZip", binding.etZip.text.toString())
            .putString("preferredUnits", binding.spinnerUnits.selectedItem.toString())
            .apply()
    }
}