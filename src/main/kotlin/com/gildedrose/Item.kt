package com.gildedrose

data class Item(
    var name: String,
    var sellIn: Int,
    var quality: Int
) {
    override fun toString(): String {
        return "$name, $sellIn, $quality"
    }
}

fun Item.decrementSellIn() { sellIn-- }

fun Item.decrementQuality(value: Int = 1) {
    if (quality > 0) {
        quality -= value
    }

    /**
     * This case can happen when value is greater than 1
     */
    if (quality < 0) {
        quality = 0
    }
}

fun Item.incrementQuality() {
    if (quality < 50) {
        quality++
    }
}
