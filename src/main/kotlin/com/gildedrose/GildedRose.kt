package com.gildedrose

class GildedRose(
    var items: Array<Item>
) {
    fun updateQuality() {
        items.forEach { item ->
            val category = getCategoryForItem(item)
            category.updateQuality(item)
            category.updateSellIn(item)
            if (item.sellIn < 0) {
                category.updateSellInPassed(item)
            }
        }
    }

    private fun getCategoryForItem(item: Item): Category {
        return when {
            item.name == AGED_BRIE -> AgedBrieCategory
            item.name == BACKSTAGE_PASSES -> BackstageCategory
            item.name == SULFURAS -> SulfurasCategory
            item.name.startsWith(CONJURED) -> ConjuredCategory
            else -> RegularCategory
        }
    }
}

internal const val AGED_BRIE = "Aged Brie"
internal const val BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert"
internal const val SULFURAS = "Sulfuras, Hand of Ragnaros"
internal const val CONJURED = "Conjured"
