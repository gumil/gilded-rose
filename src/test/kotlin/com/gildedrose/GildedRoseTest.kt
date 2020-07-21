package com.gildedrose

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class GildedRoseTest {

    @Test
    fun `At the end of each day our system lowers both values for every item`() {
        val days = Random.nextInt(1, 10)
        val name1 = Random.nextInt().toString()
        val name2 = Random.nextInt().toString()
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 20
        val quality2 = 30
        val item1 = Item(name1, sellIn1, quality1)
        val item2 = Item(name2, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(name1, sellIn1 - days, quality1 - days)
        val expectedItem2 = Item(name2, sellIn2 - days, quality2 - days)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `Once the sell by date has passed, Quality degrades twice as fast`() {
        val days = 10
        val name1 = Random.nextInt().toString()
        val name2 = Random.nextInt().toString()
        val sellIn1 = 0
        val sellIn2 = 5
        val quality1 = 20
        val quality2 = 30
        val item1 = Item(name1, sellIn1, quality1)
        val item2 = Item(name2, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(name1, sellIn1 - days, 0)
        val expectedItem2 = Item(name2, sellIn2 - days, 15)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `The Quality of an item is never negative`() {
        val days = Random.nextInt(10, 20)
        val name1 = Random.nextInt().toString()
        val name2 = Random.nextInt().toString()
        val sellIn1 = 0
        val sellIn2 = 5
        val sellIn3 = 10
        val quality1 = 5
        val quality2 = 10
        val quality3 = 15
        val item1 = Item(name1, sellIn1, quality1)
        val item2 = Item(name2, sellIn2, quality2)
        val item3 = Item(CONJURED, sellIn3, quality3)
        val items = arrayOf(item1, item2, item3)
        val expectedItem1 = Item(name1, sellIn1 - days, 0)
        val expectedItem2 = Item(name2, sellIn2 - days, 0)
        val expectedItem3 = Item(CONJURED, sellIn3 - days, 0)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items[0])
        assertEquals(expectedItem2, gildedRose.items[1])
        assertEquals(expectedItem3, gildedRose.items[2])
    }

    @Test
    fun `"Aged Brie" actually increases in Quality the older it gets`() {
        val days = Random.nextInt(10)
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 20
        val quality2 = 30
        val item1 = Item(AGED_BRIE, sellIn1, quality1)
        val item2 = Item(AGED_BRIE, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(AGED_BRIE, sellIn1 - days, quality1 + days)
        val expectedItem2 = Item(AGED_BRIE, sellIn2 - days, quality2 + days)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Aged Brie" actually increases in Quality the older it gets and passed sell in date`() {
        val days = 15
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 20
        val quality2 = 30
        val item1 = Item(AGED_BRIE, sellIn1, quality1)
        val item2 = Item(AGED_BRIE, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(AGED_BRIE, sellIn1 - days, 40)
        val expectedItem2 = Item(AGED_BRIE, sellIn2 - days, quality2 + days)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `The Quality of an item is never more than 50`() {
        val days = Random.nextInt(1, 10)
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 49
        val quality2 = 60
        val item1 = Item(AGED_BRIE, sellIn1, quality1)
        val item2 = Item(AGED_BRIE, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(AGED_BRIE, sellIn1 - days, 50)
        val expectedItem2 = Item(AGED_BRIE, sellIn2 - days, quality2)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Sulfuras", being a legendary item, never has to be sold or decreases in Quality`() {
        val days = Random.nextInt(20)
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 10
        val quality2 = 20
        val item1 = Item(SULFURAS, sellIn1, quality1)
        val item2 = Item(SULFURAS, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(SULFURAS, sellIn1, quality1)
        val expectedItem2 = Item(SULFURAS, sellIn2, quality2)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Backstage passes" increases in Quality as its SellIn value approaches Quality increases by 2 when there are 10 days or less`() {
        val days = 5
        val sellIn1 = 10
        val sellIn2 = 16
        val quality1 = 10
        val quality2 = 20
        val item1 = Item(BACKSTAGE_PASSES, sellIn1, quality1)
        val item2 = Item(BACKSTAGE_PASSES, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(BACKSTAGE_PASSES, sellIn1 - days, quality1 + days * 2)
        val expectedItem2 = Item(BACKSTAGE_PASSES, sellIn2 - days, quality2 + days)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Backstage passes" increases in Quality as its SellIn value approaches Quality increases by 3 when there are 5 days or less`() {
        val days = 5
        val sellIn1 = 5
        val sellIn2 = 6
        val quality1 = 10
        val quality2 = 20
        val item1 = Item(BACKSTAGE_PASSES, sellIn1, quality1)
        val item2 = Item(BACKSTAGE_PASSES, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(BACKSTAGE_PASSES, sellIn1 - days, quality1 + days * 3)
        val expectedItem2 = Item(BACKSTAGE_PASSES, sellIn2 - days, 34)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Backstage passes" Quality drops to 0 after the concert`() {
        val days = 5
        val sellIn1 = 4
        val sellIn2 = 3
        val quality1 = 10
        val quality2 = 20
        val item1 = Item(BACKSTAGE_PASSES, sellIn1, quality1)
        val item2 = Item(BACKSTAGE_PASSES, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(BACKSTAGE_PASSES, sellIn1 - days, 0)
        val expectedItem2 = Item(BACKSTAGE_PASSES, sellIn2 - days, 0)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }

    @Test
    fun `"Conjured" items degrade in Quality twice as fast as normal items`() {
        val days = Random.nextInt(1, 10)
        val sellIn1 = 10
        val sellIn2 = 15
        val quality1 = 20
        val quality2 = 30
        val item1 = Item(CONJURED, sellIn1, quality1)
        val item2 = Item(CONJURED, sellIn2, quality2)
        val items = arrayOf(item1, item2)
        val expectedItem1 = Item(CONJURED, sellIn1 - days, quality1 - days * 2)
        val expectedItem2 = Item(CONJURED, sellIn2 - days, quality2 - days * 2)

        val gildedRose = GildedRose(items)

        repeat(days) {
            gildedRose.updateQuality()
        }

        assertEquals(expectedItem1, gildedRose.items.first())
        assertEquals(expectedItem2, gildedRose.items.last())
    }
}
