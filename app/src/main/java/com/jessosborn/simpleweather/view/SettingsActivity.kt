package com.jessosborn.simpleweather.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.jessosborn.simpleweather.R
import com.jessosborn.simpleweather.databinding.ActivitySettingsBinding
import com.jessosborn.simpleweather.utils.DataStoreUtil
import com.jessosborn.simpleweather.utils.isValidZip
import kotlinx.coroutines.runBlocking

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populateSpinnerArray()
        binding.apply {
            etZip.setText(
                runBlocking { DataStoreUtil.getString(applicationContext, DataStoreUtil.USER_ZIP) }
            )
            btnSave.setOnClickListener {
                if (etZip.text.toString().isValidZip()) {
                    saveInputs()
                    navigateToMain()
                } else {
                    etZip.error = getString(R.string.error_enter_valid_zip)
                }
            }
        }
    }

    private fun populateSpinnerArray() {
        ArrayAdapter.createFromResource(
            this@SettingsActivity,
            R.array.spinner_units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice)
            apply {
                binding.spinnerUnits.adapter = adapter
                binding.spinnerUnits.setSelection(
                    adapter.getPosition(
                        runBlocking {
                            DataStoreUtil.getString(applicationContext, DataStoreUtil.USER_UNITS)
                        }
                    )
                )
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun saveInputs() {
        runBlocking {
            DataStoreUtil.apply {
                saveString(
                    applicationContext, USER_UNITS, binding.spinnerUnits.selectedItem.toString()
                )
                saveString(
                    applicationContext, USER_ZIP, binding.etZip.text.toString()
                )
            }
        }
    }
}
