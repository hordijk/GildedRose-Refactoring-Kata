package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.ProductNames.AGED_BRIE
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class AgedBrieUpdatePolicyTest {

    @TestFactory
    fun `Aged Brie actually increases in Quality the older it gets`() = listOf(
        Item(AGED_BRIE, 1, 48) to 49,
        Item(AGED_BRIE, 0, 50) to 50,
        Item(AGED_BRIE, 0, 49) to 50,
        Item(AGED_BRIE, 0, 48) to 50,
        Item(AGED_BRIE, 0, 0) to 2,
        Item(AGED_BRIE, -1, 0) to 2
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            AgedBrieUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @Test
    fun `sellIn degrades with 1`() {
        val item = Item(AGED_BRIE, 1, 48)
        AgedBrieUpdatePolicy().update(item)

        assertEquals(0, item.sellIn)
    }
}
