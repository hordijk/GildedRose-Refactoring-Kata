package com.gildedrose

fun Item.isSellInExpired() : Boolean {
    return this.sellIn < 0
}

fun Item.decreaseSellIn() {
    this.sellIn -= 1
}

fun Item.changeQualityWithinAllowedRangeWith(change: Int) {
    val newQuality = this.quality + change
    this.quality = newQuality.coerceIn(0, 50)
}

