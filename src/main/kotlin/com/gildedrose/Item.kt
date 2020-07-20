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

internal const val AGED_BRIE = "Aged Brie"
internal const val BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert"
internal const val SULFURAS = "Sulfuras, Hand of Ragnaros"
internal const val CONJURED = "Conjured"
