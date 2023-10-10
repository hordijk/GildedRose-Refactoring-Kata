package com.gildedrose

import com.gildedrose.updatepolicy.UpdatePolicyFactory

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val item = items[i]
            UpdatePolicyFactory.updatePolicyForItem(item).update(item)
        }
    }
}

