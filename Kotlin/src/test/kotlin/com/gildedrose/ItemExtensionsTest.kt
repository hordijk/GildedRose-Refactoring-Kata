package com.gildedrose

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class ItemExtensionsTest {

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

    @ParameterizedTest(name = "For item with quality: {0} and a change of {1} quality results in {2}")
    @CsvSource(
        "1, -1, 0",
        "1, -2, 0",
        "0, 0, 0",
        "0, 1, 1",
        "48, 2, 50",
        "48, 3, 50",
        "49, 2, 50",
        "50, 0, 50",
        "50, 1, 50",
    )
    fun `changeQualityWithinAllowedRangeWith change quality within boundaries (never negative and not above 50)`(
        quality: Int,
        change: Int,
        expectedQuality: Int
    ) {
        val item = Item("brandName", 0, quality)
        item.changeQualityWithinAllowedRangeWith(change)

        assertEquals(expectedQuality, item.quality)
    }

    @ParameterizedTest(name = "For item with sellIn: {0} invoke decreaseSellIn results in sellIn {2}")
    @CsvSource(
        "1, 0",
        "0, -1",
    )
    fun `decreaseSellIn decrease sell by one`(sellIn: Int, expectedSellIn: Int) {
        val item = Item("brandName", sellIn, 0)
        item.decreaseSellIn()

        assertEquals(expectedSellIn, item.sellIn)
    }
}
