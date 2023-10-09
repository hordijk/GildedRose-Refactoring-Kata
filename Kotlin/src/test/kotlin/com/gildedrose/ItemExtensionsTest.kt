package com.gildedrose

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ItemExtensionsTest {
    @ParameterizedTest(name = "For item with quality: {0} isBelowMaxQuality is true")
    @ValueSource(ints = [0, 49])
    fun `isBelowMaxQuality is true`(quality: Int) {
        val item = Item("brandName", 0, quality)
        assertTrue(item.isBelowMaxQuality())
    }

    @ParameterizedTest(name = "For item with quality: {0} isBelowMaxQuality is false")
    @ValueSource(ints = [50, 51])
    fun `isBelowMaxQuality is false`(quality: Int) {
        val item = Item("brandName", 0, quality)
        assertFalse(item.isBelowMaxQuality())
    }

    @ParameterizedTest(name = "For item with quality: {0} isAboveMinQuality is true")
    @ValueSource(ints = [1])
    fun `isAboveMinQuality is true`(quality: Int) {
        val item = Item("brandName", 0, quality)
        assertTrue(item.isAboveMinQuality())
    }

    @ParameterizedTest(name = "For item with quality: {0} isAboveMinQuality is false")
    @ValueSource(ints = [-1, 0])
    fun `isAboveMinQuality is false`(quality: Int) {
        val item = Item("brandName", 0, quality)
        assertFalse(item.isAboveMinQuality())
    }

    @ParameterizedTest(name = "For item with sellIn: {0} isSellInExpired is false")
    @ValueSource(ints = [0, 1])
    fun `isSellInExpired is false`(sellIn: Int) {
        val item = Item("brandName", sellIn, 0)
        assertFalse(item.isSellInExpired())
    }

    @ParameterizedTest(name = "For item with sellIn: {0} isSellInExpired is true")
    @ValueSource(ints = [-1])
    fun `isSellInExpired is true`(sellIn: Int) {
        val item = Item("brandName", sellIn, 0)
        assertTrue(item.isSellInExpired())
    }
}
