package com.gildedrose

import com.gildedrose.ProductNames.AGED_BRIE
import com.gildedrose.ProductNames.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT
import com.gildedrose.ProductNames.CONJURED_MANA_CAKE
import com.gildedrose.ProductNames.SULFURAS_HAND_OF_RAGNAROS
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.IntStream
import java.util.stream.Stream

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = listOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)
    }

    @TestFactory
    fun `updateQuality with texttest fixture matches expected output`(): Stream<DynamicTest> {
        // Given
        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20), //
            Item(AGED_BRIE, 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80), //
            Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 15, 20),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 10, 49),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 5, 49),
            Item(CONJURED_MANA_CAKE, 3, 6)
        )

        val expectedResult = listOf(
            Item("+5 Dexterity Vest", 9, 19), //
            Item(AGED_BRIE, 1, 1), //
            Item("Elixir of the Mongoose", 4, 6), //
            Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80), //
            Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 14, 21),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 9, 50),
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 4, 50),
            Item(CONJURED_MANA_CAKE, 2, 4)
        )
        val app = GildedRose(items)
        // When
        app.updateQuality()

        return IntStream.range(0, items.size).mapToObj {
            val item = app.items[it]
            val expectedItem = expectedResult[it]
            dynamicTest(
                "Entry $it: updateQuality results in $expectedItem"
            ) { item shouldBeEqualToComparingFields expectedItem }
        }
    }
}
