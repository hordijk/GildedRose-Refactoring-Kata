package com.gildedrose

const val BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert"

const val SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros"

const val AGED_BRIE = "Aged Brie"

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val item = items[i]
            lowerSellIn(item)
            when (item.name) {
                AGED_BRIE -> {
                    val changeQualityWith = if (item.isSellInExpired()) 2 else 1
                    item.changeQualityWithinAllowedRange(changeQualityWith)
                }
                BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT -> {
                    if (item.isSellInExpired()) {
                        item.quality = 0
                    } else {
                        increaseQualityForBackstagePasses(item)
                    }
                }
                SULFURAS_HAND_OF_RAGNAROS -> {
                    // Keep item as is
                }
                else -> {
                    val changeQualityWith = if (item.isSellInExpired()) -2 else -1
                    item.changeQualityWithinAllowedRange(changeQualityWith)
                }
            }
        }
    }

    private fun lowerSellIn(item: Item) {
        if (item.name != SULFURAS_HAND_OF_RAGNAROS) {
            item.sellIn -= 1
        }
    }

    private fun increaseQualityForBackstagePasses(item: Item) {
        val changeQualityWith = when {
            item.sellIn < 5 -> 3
            item.sellIn < 10 -> 2
            else -> 1
        }
        item.changeQualityWithinAllowedRange(changeQualityWith)
    }

}

