package com.example.weatherapp.utils


const val baseApiUrl: String = "https://api.openweathermap.org/data/2.5/"

/**
 * Simple REGEX function to check for valid ZipCode format. Does not check if ZipCode is real
 * @author: Jess Osborn
 */
fun isValidZip(text: String): Boolean {

    val scrubbedZip = text.replace(" ", "")

    //ZipCode REGEX patterns
    val americanZipFormat = Regex("^[0-9]{5}\$")
    val canadianZipFormat = Regex("^[ABCEGHJKLMNPRSTVXY]{1}\\d{1}[A-Z]{1} *\\d{1}[A-Z]{1}\\d{1}\$")

    return (americanZipFormat.matches(scrubbedZip)) || canadianZipFormat.matches(scrubbedZip)
}