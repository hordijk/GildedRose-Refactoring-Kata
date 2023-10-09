package com.gildedrose

fun Item.isSellInExpired() : Boolean {
    return this.sellIn < 0
}

fun Item.changeQualityWithinAllowedRange(change: Int) {
    val newQuality = this.quality + change
    this.quality = newQuality.coerceIn(0, 50)
}

