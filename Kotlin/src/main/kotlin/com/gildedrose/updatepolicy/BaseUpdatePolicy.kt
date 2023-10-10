package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.decreaseSellIn

abstract class BaseUpdatePolicy : UpdatePolicy {

    override fun update(item: Item) {
        item.decreaseSellIn()
        updateQuality(item)
    }

    abstract fun updateQuality(item: Item)
}
