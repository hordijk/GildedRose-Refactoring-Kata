package com.gildedrose

import com.gildedrose.updatepolicy.UpdatePolicyFactory

class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        items.forEach { item -> UpdatePolicyFactory.updatePolicyForItem(item).update(item) }
    }
}

