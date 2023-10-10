package com.gildedrose.updatepolicy

import com.gildedrose.AGED_BRIE
import com.gildedrose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT
import com.gildedrose.Item
import com.gildedrose.SULFURAS_HAND_OF_RAGNAROS
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UpdatePolicyFactoryTest {
    @Test
    fun `updatePolicyForItem for aged brie returns AgedBrieUpdatePolicy`() {
        val item = Item(AGED_BRIE, 0, 0)
        val updatePolicyForItem = UpdatePolicyFactory.updatePolicyForItem(item)
        updatePolicyForItem.shouldBeTypeOf<AgedBrieUpdatePolicy>()
    }

    @Test
    fun `updatePolicyForItem for sulfuras returns KeepExistingValuesUpdatePolicy`() {
        val item = Item(SULFURAS_HAND_OF_RAGNAROS, 0, 0)
        val updatePolicyForItem = UpdatePolicyFactory.updatePolicyForItem(item)
        updatePolicyForItem.shouldBeTypeOf<KeepExistingValuesUpdatePolicy>()
    }

    @Test
    fun `updatePolicyForItem for backstage passes returns BackstagePassesUpdatePolicy`() {
        val item = Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 0, 0)
        val updatePolicyForItem = UpdatePolicyFactory.updatePolicyForItem(item)
        updatePolicyForItem.shouldBeTypeOf<BackstagePassesUpdatePolicy>()
    }

    @ParameterizedTest(name = "For item with name: {0} DefaultUpdatePolicy is used")
    @ValueSource(strings = ["custom", "not mapped"])
    fun `defaultUpdatePolicy is returned for names not mapped`(name: String) {
        val item = Item(name, 0, 0)
        val updatePolicyForItem = UpdatePolicyFactory.updatePolicyForItem(item)
        updatePolicyForItem.shouldBeTypeOf<DefaultUpdatePolicy>()
    }
}
