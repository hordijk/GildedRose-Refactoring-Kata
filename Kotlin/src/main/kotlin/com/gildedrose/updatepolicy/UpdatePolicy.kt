package com.gildedrose.updatepolicy

import com.gildedrose.Item

interface UpdatePolicy {

    fun update(item: Item)
}
