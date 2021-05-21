package com.example.weatherapp.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilsTest {

    @Test
    fun validUsZipIsValid() {
        assertTrue(isValidZip("77469"))
        assertTrue(isValidZip("11111"))
    }

    @Test
    fun invalidUsZipIsInvalid() {
        assertFalse(isValidZip("774697"))
        assertFalse(isValidZip("11111a"))
    }

    @Test
    fun validCanadianZipIsInvalid() {
        assertFalse(isValidZip("a1a1a1"))
        assertFalse(isValidZip("v2v2v2"))
    }

    @Test
    fun invalidCanadianZipIsInvalid() {
        assertFalse(isValidZip("a1a111"))
        assertFalse(isValidZip("a1a1a"))
    }
}