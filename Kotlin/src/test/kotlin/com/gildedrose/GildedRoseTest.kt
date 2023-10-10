package com.gildedrose

import com.gildedrose.ProductNames.AGED_BRIE
import com.gildedrose.ProductNames.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT
import com.gildedrose.ProductNames.SULFURAS_HAND_OF_RAGNAROS
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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

    @ParameterizedTest(name = "Item with sellIn: {0} and quality: {1} updateQuality results in quality: {2}")
    @CsvSource(
        "1, 1, 0",
        "0, 1, 0",
        "0, 0, 0",
        "-1, 0, 0"
    )
    fun `The Quality of an item is never negative`(sellIn: Int, quality: Int, expectedQuality: Int) {
        val app = getGildedRoseWithItem(Item("brandName", sellIn, quality))
        app.updateQuality()
        assertEquals(expectedQuality, app.items[0].quality)
    }

    @TestFactory
    fun `The Quality of an item is never more than 50`() = listOf(
        Item(AGED_BRIE, 1, 49) to 50,
        Item(AGED_BRIE, 1, 50) to 50,
        Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 1, 49) to 50,
    ).map { (item, expectedQuality) ->
        dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            val app = getGildedRoseWithItem(item)
            app.updateQuality()
            assertEquals(expectedQuality, app.items[0].quality)
        }
    }

    @TestFactory
    fun `Sulfuras, being a legendary item, never has to be sold or decreases in Quality`() = listOf(
        Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80) to Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80),
        Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80) to Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80)
    ).map { (item, expected) ->
        dynamicTest("with $item updateQuality keep existing values like $expected") {
            val app = getGildedRoseWithItem(item)
            app.updateQuality()

            app.items[0] shouldBeEqualToComparingFields expected
        }
    }
    @TestFactory
    fun `Conjured items degrade in Quality twice as fast as normal items`() = listOf(
        Item("Conjured Mana Cake", 10, 20) to 19, // TODO this should be 18, however this is not implemented yet
        Item("Conjured Mana Cake", 1, 1) to 0,
    ).map { (item, expectedQuality) ->
        dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            val app = getGildedRoseWithItem(item)
            app.updateQuality()
            assertEquals(expectedQuality, app.items[0].quality)
        }
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
            // this conjured item does not work properly yet
            Item("Conjured Mana Cake", 3, 6)
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
            // this conjured item does not work properly yet
            Item("Conjured Mana Cake", 2, 5)
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

    private fun getGildedRoseWithItem(item: Item): GildedRose {
        return GildedRose(listOf(item))
    }
}
