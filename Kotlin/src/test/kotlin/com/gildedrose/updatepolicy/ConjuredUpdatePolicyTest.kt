package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.ProductNames
import com.gildedrose.ProductNames.CONJURED_MANA_CAKE
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class ConjuredUpdatePolicyTest {

    @TestFactory
    fun `Conjured items degrade in Quality twice as fast as normal items`() = listOf(
    Item(CONJURED_MANA_CAKE, 10, 4) to 2,
    Item(CONJURED_MANA_CAKE, 9, 1) to 0,
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item update results in a quality of $expectedQuality") {
            ConjuredUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @Test
    fun `sellIn degrades with 1`() {
        val item = Item(ProductNames.AGED_BRIE, 1, 48)
        ConjuredUpdatePolicy().update(item)

        assertEquals(0, item.sellIn)
    }
}

