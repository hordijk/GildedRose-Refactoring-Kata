package com.gildedrose.updatepolicy

import com.gildedrose.Item
import com.gildedrose.SULFURAS_HAND_OF_RAGNAROS
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class KeepExistingValuesUpdatePolicyTest {

    @TestFactory
    fun `Item keeps existing sellIn and quality values`() = listOf(
        Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80) to Item(SULFURAS_HAND_OF_RAGNAROS, 0, 80),
        Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80) to Item(SULFURAS_HAND_OF_RAGNAROS, -1, 80),
        Item("brand", 0, 23) to Item("brand", 0, 23)
    ).map { (item, expected) ->
        DynamicTest.dynamicTest("with $item updateQuality keep existing values like $expected") {
            item shouldBeEqualToComparingFields expected
        }
    }
}
