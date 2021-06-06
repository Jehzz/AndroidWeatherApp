package com.jessosborn.simpleweather.utils

fun String.isValidZip(): Boolean {
    return isUsZip(this) ||
            isCanadianZip(this) ||
            isUkZip(this)
}

fun isUsZip(zip: String): Boolean {
    val scrubbedZip = zip.replace(" ", "").uppercase()
    val americanZipFormat = Regex("^[0-9]{5}\$")
    return americanZipFormat.matches(scrubbedZip)
}

fun isCanadianZip(zip: String): Boolean {
    val scrubbedZip = zip.replace(" ", "").uppercase()
    val canadianZipFormat = Regex("^[ABCEGHJKLMNPRSTVXY]\\d[A-Z]\$")
    return canadianZipFormat.matches(scrubbedZip)
}

fun isUkZip (zip: String): Boolean {
    val scrubbedZip = zip.replace(" ", "").uppercase()
    val ukZipFormat = Regex("^[A-Z]{2}\\d")
    return ukZipFormat.matches(scrubbedZip)
}

fun getCountryFromZip(zip: String): String {
    val scrubbedZip = zip.replace(" ", "").uppercase()
    when {
        isUsZip(scrubbedZip) -> return "us"
        isCanadianZip(scrubbedZip) -> return "ca"
        isUkZip(scrubbedZip) -> return "gb"
        else -> return "NULL"
    }
}