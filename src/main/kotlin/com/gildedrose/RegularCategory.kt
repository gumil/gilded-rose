package com.gildedrose

class RegularCategory: Category {
    override fun updateQuality(item: Item) {
        item.decrementQuality()
    }

    override fun updateSellInPassed(item: Item) {
        item.decrementQuality()
    }
}
