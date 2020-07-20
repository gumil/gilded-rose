package com.gildedrose

class BackstageCategory: Category {
    override fun updateQuality(item: Item) {
        item.incrementQuality()

        if (item.sellIn < 6) {
            item.incrementQuality()
        }

        if (item.sellIn < 11) {
            item.incrementQuality()
        }
    }

    override fun updateSellInPassed(item: Item) {
        item.quality = 0
    }
}
