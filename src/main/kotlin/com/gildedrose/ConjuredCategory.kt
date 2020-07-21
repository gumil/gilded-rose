package com.gildedrose

object ConjuredCategory: Category {
    override fun updateQuality(item: Item) {
        item.decrementQuality(2)
    }
}
