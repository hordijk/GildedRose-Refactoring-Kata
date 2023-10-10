package com.gildedrose.updatepolicy

import com.gildedrose.Item

class KeepExistingValuesUpdatePolicy : UpdatePolicy {

    override fun update(item: Item) {
        // Left blank intentionally, as the values of the item should be kept.
    }
}
