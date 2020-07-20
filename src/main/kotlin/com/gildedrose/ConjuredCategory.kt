package com.gildedrose

object ConjuredCategory: Category {
    override fun updateQuality(item: Item) {
        item.decrementQuality(2)
    }

    override fun updateSellInPassed(item: Item) {
        item.decrementQuality(2)
    }
}
