package com.gildedrose

interface Category {
    fun updateQuality(item: Item): Item
    fun updateSellIn(item: Item): Item = item.decrementSellIn()
}
