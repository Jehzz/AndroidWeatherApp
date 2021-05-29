package com.example.simpleweather.utils

fun isValidZip(text: String): Boolean {
    val scrubbedZip = text.replace(" ", "").uppercase()
    return isUsZip(scrubbedZip) || isCanadianZip(scrubbedZip)
}

fun isUsZip(zip: String): Boolean {
    val americanZipFormat = Regex("^[0-9]{5}\$")
    return americanZipFormat.matches(zip)
}

fun isCanadianZip(zip: String): Boolean {
    val canadianZipFormat = Regex("^[ABCEGHJKLMNPRSTVXY]\\d[A-Z]\$")
    return canadianZipFormat.matches(zip)
}

fun getCountryFromZip(zip: String): String {
    if (isUsZip(zip)) {
        return "us"
    } else if (isCanadianZip(zip)) {
        return "ca"
    } else return "us"
}