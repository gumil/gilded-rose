package com.gildedrose

object RegularCategory: Category {
    override fun updateQuality(item: Item) {
        item.decrementQuality()
    }
}
