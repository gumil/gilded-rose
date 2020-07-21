# Gilded Rose
This is a solution to the [Gilded Rose refactoring kata](https://github.com/emilybache/GildedRose-Refactoring-Kata)

## Creating the tests
Before touching the code is a good idea to make unit tests that satisfies all the
[specification](https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/master/GildedRoseRequirements.txt)
of this kata. This is to make sure when changes happen the code still works as expected.

The initial test suite can be found
[here](https://github.com/gumil/gilded-rose/blob/27bd105ffb72ca05c98db415f7bfa1a6b4d08541/src/test/kotlin/com/gildedrose/GildedRoseTest.kt).
It currently fails on two test cases because the tests expect the `Conjured` items to be handled.
This feature needs to be implemented first.

When writing these tests it builds the confidence that the code can be changed as many times as we want since we can
immediately verify if there are unwanted changes.

## Analyzing the code
Now we have the tests, then we can start refactoring this code. The code is not straightforward. You have to read all
the lines to understand what is going on.

To make sense of the code we can divide it into three parts:

### 1. Updating the quality or value of the item.
```kotlin
if (!items[i].name.equals("Aged Brie") && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
    if (items[i].quality > 0) {
        if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
            items[i].quality = items[i].quality - 1
        }
    }
} else {
    if (items[i].quality < 50) {
        items[i].quality = items[i].quality + 1

        if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (items[i].sellIn < 11) {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1
                }
            }

            if (items[i].sellIn < 6) {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1
                }
            }
        }
    }
}
```

In the code above we can either increment or decrement the quality depending on the category. This is standard on the
items and can be extracted to an extension function:
```kotlin
fun Item.decrementQuality() {
    if (quality > 0) {
        quality -= value
    }
}

fun Item.incrementQuality() {
    if (quality < 50) {
        quality++
    }
}
```
This way the code does not have to repeat itself in verifying the restrictions of the quality and incrementing the value.
It can simply call this extension function.

### 2. Updating the sell in days
 ```kotlin
if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
    items[i].sellIn = items[i].sellIn - 1
}
```
This part is straightforward. The code decreases the `sellIn` value on each iteration except for `Sulfuras` which is a
legendary item.

### 3. Updating the quality again when sell in date has passed
```kotlin
if (items[i].sellIn < 0) {
    if (!items[i].name.equals("Aged Brie")) {
        if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (items[i].quality > 0) {
                if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                    items[i].quality = items[i].quality - 1
                }
            }
        } else {
            items[i].quality = items[i].quality - items[i].quality
        }
    } else {
        if (items[i].quality < 50) {
            items[i].quality = items[i].quality + 1
        }
    }
}
```

In this part, updates the quality similarly to step 1. The difference is that in this part the `sellIn` value is taken
into consideration. If the date has passed then it either increments or decrements its value. The extension functions
introduced earlier can be reused here.

## Representing the categories
In the specification we can divide the categories into five:
1. [Regular](src/main/kotlin/com/gildedrose/RegularCategory.kt)
2. [Aged Brie](src/main/kotlin/com/gildedrose/AgedBrieCategory.kt)
3. [Sulfuras](src/main/kotlin/com/gildedrose/SulfurasCategory.kt)
4. [Backstage Passes](src/main/kotlin/com/gildedrose/BackstageCategory.kt)

Each category has a different way in updating its value at the end of the day. Previously we listed the parts of the 
code to understand it, and we can represent these parts into an interface:

```kotlin
interface Category {
    fun updateQuality(item: Item)
    fun updateSellIn(item: Item) { item.decrementSellIn() }
}
```

Then we can create a class for each category to implement this interface. In each class we can now write code that is 
solely responsible to its specification. This way it is easier to understand what each category does to its value. 
A default implementation on updating the `sellIn` value since it's similar on all the categories except one which is 
the `Sulfuras` category.

## Refactoring the GildedRose class
We've created this classes but the `GildedRose` is still untouched. Now we can edit this class and be comfortable that 
we do not unintentionally remove a feature since we have our unit tests to protect us from that.

Putting it all together, the refactored `updateQuality()` will look like this:
```kotlin
    fun updateQuality() {
        items.forEach { item ->
            val category = getCategoryForItem(item)
            category.updateQuality(item)
            category.updateSellIn(item)
            if (item.sellIn < 0) {
                category.updateQuality(item)
            }
        }
    }

    private fun getCategoryForItem(item: Item): Category {
        return when {
            item.name == AGED_BRIE -> AgedBrieCategory
            item.name == BACKSTAGE_PASSES -> BackstageCategory
            item.name == SULFURAS -> SulfurasCategory
            else -> RegularCategory
        }
    }
```

As you can see the code is now easier to understand, and it makes much more sense now. The three steps we mentioned 
earlier are abstracted from the specific classes. You can read the code easily and know what it actually does without
much effort.

## Adding the Conjured category
Our tests are still failing because we have not implemented the `Conjured` category yet. It should be straightforward
now since we already have the `Category` interface. We have to create a new class
[ConjuredCategory](src/main/kotlin/com/gildedrose/ConjuredCategory.kt) that implements this interface and attach it to 
our `GildedRose` class.

Apart from creating the class, we can also refactor our `incrementQuality` function to adjust the increment value:
```kotlin
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
```
With the initial refactoring we made, we can do continuous refactoring to make the code tidy and not further introduce
technical debt.

## Text-Based Approval Testing
We can further verify that our refactored code works by testing the code by comparing the output of the code from a 
text file. We can use the sample from [here](https://github.com/emilybache/GildedRose-Refactoring-Kata/tree/master/texttests). 
With few adjustments to make sure the `Conjured` category is being properly handled in the expected output.

The changes can be viewed in this [commit](https://github.com/gumil/gilded-rose/commit/2639c1427f00101c255beafcfd668d2cd24ee659).

