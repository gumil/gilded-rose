package com.gildedrose

object AgedBrieCategory: Category {
    override fun updateQuality(item: Item) {
        item.incrementQuality()
    }
}
