package com.gildedrose

fun Item.isBelowMaxQuality() : Boolean {
    return this.quality < 50
}

fun Item.isAboveMinQuality() : Boolean {
    return this.quality > 0
}
