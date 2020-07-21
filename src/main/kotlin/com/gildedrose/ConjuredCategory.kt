package com.gildedrose

object ConjuredCategory: Category {
    override fun updateQuality(item: Item): Item =
        item.decrementQuality(2)
}
