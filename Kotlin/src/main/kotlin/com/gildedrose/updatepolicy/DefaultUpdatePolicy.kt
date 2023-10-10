package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.changeQualityWithinAllowedRangeWith
import com.gildedrose.isSellInExpired

class DefaultUpdatePolicy : BaseUpdatePolicy() {

    override fun updateQuality(item: Item) {
        val changeQualityWith = if (item.isSellInExpired()) -2 else -1
        item.changeQualityWithinAllowedRangeWith(changeQualityWith)
    }
}
