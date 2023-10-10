package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TexttestInspiredTest {

    @Test
    fun `texttext fixture test data output remains the same`() {
        val items = listOf(
            Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            Item("Conjured Mana Cake", 3, 6)
        )
        val app = GildedRose(items)

        var result = ""
        for (i in 0 until 31) {
            result += getResultsForDay(i, items)
            app.updateQuality()
        }

        assertEquals(
            TexttestInspiredTest::class.java.getResource("expectedOutputFor30Days.txt").readText(),
            result
        )
    }

    private fun getResultsForDay(day: Int, items: List<Item>): String {
        var result = "-------- day $day --------\n"
        result += "name, sellIn, quality\n"
        for (item in items) {
            result += "$item\n"
        }
        result += "\n"
        return result
    }
}
