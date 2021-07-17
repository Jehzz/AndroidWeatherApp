package com.jessosborn.simpleweather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isOnline(context: Context): Boolean {
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
