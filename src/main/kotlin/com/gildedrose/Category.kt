package com.gildedrose

interface Category {
    fun updateQuality(item: Item)
    fun updateSellIn(item: Item) { item.decrementSellIn() }
}
