package com.example.weatherapp.utils

/**
 * Simple REGEX function to check the submitted zipcode format. Does not check if zipcode is real
 * @author: Jess Osborn
 */

val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"
val key: String = "ca3efb1692ca390683b47b41ade98581"

fun isValidZip(text: String): Boolean {
    //TODO: Add Canadian Regex pattern, check against both
    val scrubbedZip = text.replace(" ", "")

    val americanZipFormat = Regex("^[0-9]{5}\$")
    val canadianZipFormat = Regex("^[ABCEGHJKLMNPRSTVXY]{1}\\d{1}[A-Z]{1} *\\d{1}[A-Z]{1}\\d{1}\$")

    return (americanZipFormat.matches(scrubbedZip)) || canadianZipFormat.matches(scrubbedZip)
}