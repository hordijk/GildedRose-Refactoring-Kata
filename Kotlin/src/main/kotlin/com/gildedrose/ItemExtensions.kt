package com.gildedrose

fun Item.isBelowMaxQuality() : Boolean {
    return this.quality < 50
}

fun Item.isAboveMinQuality() : Boolean {
    return this.quality > 0
}

fun Item.isSellInExpired() : Boolean {
    return this.sellIn < 0
}
