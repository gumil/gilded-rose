package com.gildedrose

data class Item(
    val name: String,
    val sellIn: Int,
    val quality: Int
) {
    override fun toString(): String {
        return "$name, $sellIn, $quality"
    }
}

fun Item.decrementSellIn(): Item = Item(name, sellIn - 1, quality)

fun Item.decrementQuality(value: Int = 1): Item {
    var currentQuality = quality
    if (currentQuality > 0) {
        currentQuality -= value
    }

    /**
     * This case can happen when value is greater than 1
     */
    if (currentQuality < 0) {
        currentQuality = 0
    }
    return Item(name, sellIn, currentQuality)
}

fun Item.incrementQuality(): Item {
    if (quality < 50) {
        return Item(name, sellIn, quality + 1)
    }
    return this
}
