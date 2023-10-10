package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.changeQualityWithinAllowedRange
import com.gildedrose.isSellInExpired

class ConjuredUpdatePolicy : BaseUpdatePolicy() {

    override fun updateQuality(item: Item) {
        val changeQualityWith = if (item.isSellInExpired()) -4 else -2
        item.changeQualityWithinAllowedRange(changeQualityWith)
    }
}
