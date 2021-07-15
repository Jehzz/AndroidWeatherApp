package com.jessosborn.simpleweather.utils

fun String.isValidZip(): Boolean {
    return isUsZip(this) ||
            isCanadianZip(this) ||
            isUkZip(this)
}

fun isUsZip(zip: String): Boolean {
    val americanZipFormat = Regex("^[0-9]{5}\$")
    return americanZipFormat.matches(zip.uppercase())
}

fun isCanadianZip(zip: String): Boolean {
    val canadianZipFormat = Regex("^[ABCEGHJKLMNPRSTVXY]\\d[A-Z]\$")
    return canadianZipFormat.matches(zip.uppercase())
}

fun isUkZip(zip: String): Boolean {
    val ukZipFormat = Regex("^[A-Z]{1,2}[0-9R][0-9A-Z]?\$")
    return ukZipFormat.matches(zip.uppercase())
}

fun getCountryFromZip(zip: String): String {
    return when {
        isUsZip(zip.uppercase()) -> "us"
        isCanadianZip(zip.uppercase()) -> "ca"
        isUkZip(zip.uppercase()) -> "gb"
        else -> "NULL"
    }
}
