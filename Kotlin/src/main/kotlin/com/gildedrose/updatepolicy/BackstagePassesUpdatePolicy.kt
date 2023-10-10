package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.changeQualityWithinAllowedRange
import com.gildedrose.isSellInExpired

class BackstagePassesUpdatePolicy : BaseUpdatePolicy() {

    override fun updateQuality(item: Item) {
        if (item.isSellInExpired()) {
            item.quality = 0
        } else {
            val changeQualityWith = when {
                item.sellIn < 5 -> 3
                item.sellIn < 10 -> 2
                else -> 1
            }
            item.changeQualityWithinAllowedRange(changeQualityWith)
        }
    }
}
