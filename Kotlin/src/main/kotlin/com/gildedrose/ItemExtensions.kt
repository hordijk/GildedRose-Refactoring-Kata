package com.gildedrose

private const val MINIMUM_QUALITY = 0
private const val MAXIMUM_QUALITY = 50

fun Item.isSellInExpired() : Boolean {
    return this.sellIn < 0
}

fun Item.decreaseSellIn() {
    this.sellIn -= 1
}

fun Item.changeQualityWithinAllowedRangeWith(change: Int) {
    val newQuality = this.quality + change
    this.quality = newQuality.coerceIn(MINIMUM_QUALITY, MAXIMUM_QUALITY)
}

