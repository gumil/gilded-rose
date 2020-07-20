package com.gildedrose

class AgedBrieCategory: Category {
    override fun updateQuality(item: Item) {
        item.incrementQuality()
    }

    override fun updateSellInPassed(item: Item) {
        item.incrementQuality()
    }
}
