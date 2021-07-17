package com.jessosborn.simpleweather.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ZipUtilsTest {

    @Test
    fun isValidZip() {
        assertTrue(VALID_USA_ZIP.isValidZip())
        assertTrue(VALID_CAN_ZIP.isValidZip())
        assertTrue(VALID_UK_3_ZIP.isValidZip())
        assertTrue(VALID_UK_4_ZIP.isValidZip())
    }

    @Test
    fun validUsZipIsUsZip() {
        assertTrue(isUsZip(VALID_USA_ZIP))
    }

    @Test
    fun nonUsZipIsNotUsZip() {
        assertFalse(isUsZip(VALID_CAN_ZIP))
        assertFalse(isUsZip(VALID_UK_3_ZIP))
        assertFalse(isUsZip(VALID_UK_4_ZIP))
    }

    @Test
    fun validCanZipIsCanZip() {
        assertTrue(isCanadianZip(VALID_CAN_ZIP))
    }

    @Test
    fun nonCanZipIsNotCanZip() {
        assertFalse(isCanadianZip(VALID_USA_ZIP))
        assertFalse(isCanadianZip(VALID_UK_3_ZIP))
        assertFalse(isCanadianZip(VALID_UK_4_ZIP))
    }

    @Test
    fun validUkZipIsUkZip() {
        assertTrue(isUkZip(VALID_UK_4_ZIP))
        assertTrue(isUkZip(VALID_UK_3_ZIP))
    }

    @Test
    fun nonUkZipIsNotUkZip() {
        assertFalse(isUkZip(VALID_USA_ZIP))
    }

    companion object {
        const val VALID_UK_4_ZIP = "WC2N"
        const val VALID_UK_3_ZIP = "PR6"
        const val VALID_USA_ZIP = "10168"
        const val VALID_CAN_ZIP = "M4V"
    }
}
