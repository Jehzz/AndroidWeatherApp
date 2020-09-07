package com.example.weatherapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private var myAppContext: Context? = null

        fun getApplication(): Context {
            return myAppContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        myAppContext = baseContext
    }
}