package com.gildedrose.updatepolicy

import com.gildedrose.ProductNames.AGED_BRIE
import com.gildedrose.ProductNames.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT
import com.gildedrose.Item
import com.gildedrose.ProductNames.SULFURAS_HAND_OF_RAGNAROS

object UpdatePolicyFactory {

    fun updatePolicyForItem(item: Item): UpdatePolicy {
        return when (item.name) {
            AGED_BRIE -> AgedBrieUpdatePolicy()
            BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT -> BackstagePassesUpdatePolicy()
            SULFURAS_HAND_OF_RAGNAROS -> KeepExistingValuesUpdatePolicy()
            else -> DefaultUpdatePolicy()
        }
    }
}
