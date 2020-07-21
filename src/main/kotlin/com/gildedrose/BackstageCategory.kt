package com.gildedrose

object BackstageCategory: Category {
    override fun updateQuality(item: Item) {
        if (item.sellIn < 0) {
            item.quality = 0
            return
        }

        item.incrementQuality()

        if (item.sellIn < 6) {
            item.incrementQuality()
        }

        if (item.sellIn < 11) {
            item.incrementQuality()
        }
    }
}
