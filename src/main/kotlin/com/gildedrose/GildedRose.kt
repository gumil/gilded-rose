package com.gildedrose

class GildedRose(
    items: Array<Item>
) {
    val items get() = _items.toTypedArray()

    private var _items = items.toList()

    fun updateQuality() {
        _items = _items
            .map { item ->
                val category = getCategoryForItem(item)
                val newItem = category.updateSellIn(category.updateQuality(item))
                if (newItem.sellIn < 0) {
                    return@map category.updateQuality(newItem)
                }
                return@map newItem
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
