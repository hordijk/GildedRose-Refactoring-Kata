package com.gildedrose

const val BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert"

const val SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros"

const val AGED_BRIE = "Aged Brie"

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val item = items[i]
            if (item.name != AGED_BRIE && item.name != BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT) {
                lowerQuality(item)
            } else {
                if (item.isBelowMaxQuality()) {
                    item.quality = item.quality + 1
                    increaseQualityForBackstagePasses(item)
                }
            }

            lowerSellIn(item)

            if (item.sellIn < 0) {
                if (item.name != AGED_BRIE) {
                    if (item.name != BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT) {
                        lowerQuality(item)
                    } else {
                        item.quality = 0
                    }
                } else {
                    increaseQuality(item)
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
        if (item.name == BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT) {
            if (item.sellIn < 11) {
                increaseQuality(item)
            }

            if (item.sellIn < 6) {
                increaseQuality(item)
            }
        }
    }

    private fun increaseQuality(item: Item) {
        if (item.isBelowMaxQuality()) {
            item.quality += 1
        }
    }

    private fun lowerQuality(item: Item) {
        if (item.isAboveMinQuality()) {
            if (item.name != SULFURAS_HAND_OF_RAGNAROS) {
                item.quality -= 1
            }
        }
    }

}

