package com.gildedrose.updatepolicy

import com.gildedrose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT
import com.gildedrose.Item
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class BackstagePassesUpdatePolicyTest {
    @TestFactory
    fun `Backstage passes, increases quality by 1 when there are 11 days or more`() = listOf(
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 11, 0) to 1,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 12, 2) to 3
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            BackstagePassesUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @TestFactory
    fun `Backstage passes, increases quality by 2 when there are 10 days or less`() = listOf(
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 10, 2) to 4,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 9, 2) to 4,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 8, 2) to 4,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 7, 2) to 4,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 6, 2) to 4,
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            BackstagePassesUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @TestFactory
    fun `Backstage passes, increases quality by 3 when there are 5 days or less`() = listOf(
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 5, 2) to 5,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 4, 2) to 5,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 3, 2) to 5,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 2, 2) to 5,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 1, 2) to 5,
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            BackstagePassesUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @TestFactory
    fun `Backstage passes Quality drops to 0 after the concert`() = listOf(
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 0, 3) to 0,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, -1, 0) to 0,
    ).map { (item, expectedQuality) ->
        DynamicTest.dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            BackstagePassesUpdatePolicy().update(item)
            assertEquals(expectedQuality, item.quality)
        }
    }

    @Test
    fun `sellIn degrades with 1`() {
        val item = Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 1, 48)
        BackstagePassesUpdatePolicy().update(item)

        assertEquals(0, item.sellIn)
    }

}
