package com.gildedrose.updatepolicy

import com.gildedrose.Item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DefaultUpdatePolicyTest {

    @Test
    fun `At the end of each day the sellIn degrades with 1`() {
        val item = Item("brandName", 1, 50)
        DefaultUpdatePolicy().update(item)
        assertEquals(0, item.sellIn)
    }

    @Test
    fun `At the end of each day the Quality degrades with 1`() {
        val item = Item("brandName", 1, 50)
        DefaultUpdatePolicy().update(item)
        assertEquals(49, item.quality)
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val item = Item("brandName", 0, 50)
        DefaultUpdatePolicy().update(item)
        assertEquals(48, item.quality)
    }
}

