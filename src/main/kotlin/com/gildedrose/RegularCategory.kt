package com.gildedrose

object RegularCategory: Category {
    override fun updateQuality(item: Item) {
        item.decrementQuality()
    }

    override fun updateSellInPassed(item: Item) {
        item.decrementQuality()
    }
}
