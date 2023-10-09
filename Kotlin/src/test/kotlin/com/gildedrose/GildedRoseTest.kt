package com.gildedrose

import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
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

    @Test
    fun `At the end of each day the sellIn degrades with 1`() {
        val items = listOf(Item("brandName", 1, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(0, app.items[0].sellIn)
    }

    @Test
    fun `At the end of each day the Quality degrades with 1`() {
        val items = listOf(Item("brandName", 1, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(49, app.items[0].quality)
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val items = listOf(Item("brandName", 0, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(48, app.items[0].quality)
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
    fun `Aged Brie actually increases in Quality the older it gets`() = listOf(
        Item(AGED_BRIE, 1, 48) to 49,
        Item(AGED_BRIE, 0, 50) to 50,
        Item(AGED_BRIE, 0, 49) to 50,
        Item(AGED_BRIE, 0, 48) to 50,
        Item(AGED_BRIE, 0, 0) to 2,
        Item(AGED_BRIE, -1, 0) to 2
    ).map { (item, expectedQuality) ->
        dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
            val app = getGildedRoseWithItem(item)
            app.updateQuality()
            assertEquals(expectedQuality, app.items[0].quality)
        }
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
            assertEquals(expected.quality, app.items[0].quality)
            assertEquals(expected.sellIn, app.items[0].sellIn)
        }
    }
    @Nested
    inner class backStagePassesTest {
        @TestFactory
        fun `Backstage passes, increases quality by 1 when there are 11 days or more`() = listOf(
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 11, 0) to 1,
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 12, 2) to 3,

            ).map { (item, expectedQuality) ->
            dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
                val app = getGildedRoseWithItem(item)
                app.updateQuality()
                assertEquals(expectedQuality, app.items[0].quality)
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
            dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
                val app = getGildedRoseWithItem(item)
                app.updateQuality()
                assertEquals(expectedQuality, app.items[0].quality)
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
            dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
                val app = getGildedRoseWithItem(item)
                app.updateQuality()
                assertEquals(expectedQuality, app.items[0].quality)
            }
        }

        @TestFactory
        fun `Backstage passes Quality drops to 0 after the concert`() = listOf(
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 0, 3) to 0,
            Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, -1, 0) to 0,
        ).map { (item, expectedQuality) ->
            dynamicTest("with $item updateQuality results in a quality of $expectedQuality") {
                val app = getGildedRoseWithItem(item)
                app.updateQuality()
                assertEquals(expectedQuality, app.items[0].quality)
            }
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
