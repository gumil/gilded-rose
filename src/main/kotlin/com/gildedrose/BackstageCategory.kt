package com.gildedrose

object BackstageCategory: Category {
    override fun updateQuality(item: Item): Item {
        if (item.sellIn < 0) {
            return Item(item.name, item.sellIn, 0)
        }

        var currentItem = item.incrementQuality()

        if (item.sellIn < 6) {
            currentItem = currentItem.incrementQuality()
        }

        if (item.sellIn < 11) {
            currentItem = currentItem.incrementQuality()
        }

        return currentItem
    }
}
