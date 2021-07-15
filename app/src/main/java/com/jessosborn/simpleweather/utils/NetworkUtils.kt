package com.jessosborn.simpleweather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.jessosborn.simpleweather.App.Companion.context

fun isOnline(): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
        if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) ||
            (it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        ) {
            return true
        }
    }
    return false
}
