package com.github.andrewgazelka.eulerproject.util

import com.github.andrewgazelka.eulerproject.problems.FactorResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.ceil
import kotlin.math.log2
import kotlin.math.sqrt

fun gauss(min: Int, max: Int, numberCount: Int): Int {
    return ((min + max) * numberCount) / 2
}

fun Int.largestMultiple(factor: Int): Int {
    if (this < factor) throw IllegalArgumentException("factor must be less than number")
    return (this / factor) * factor
}

fun fib(base: Pair<Long, Long> = Pair(1L, 2L)) = generateSequence(base) {
    Pair(it.second, it.first + it.second)
}.map { it.first }

val Int.isEven get() = this % 2 == 0
val Long.isEven get() = this % 2 == 0L

val Int.isOdd get() = !isEven
val Long.isOdd get() = this % 2 == 0L


fun Collection<Long>.product(): Long = reduce { acc, l -> acc * l }


fun generateTest(boolean: Boolean) = sequence {
    if (boolean) yieldAll(setOf(1, 2, 3))
    else yield(generateTest(!boolean))
}

suspend fun <A, B> Iterable<A>.cmap(f: suspend (A) -> B): List<B> = coroutineScope {
    this@cmap.map { async { f(it) } }
        .map { it.await() }
}

val Int.digits get() = this.toString().map { it.toString().toInt() }

/**
 * used in 33
 */

val Fraction.fakeDivide: Fraction?
    get() {
        val top = numerator.digits
        val bottom = denominator.digits
        require(top.size == 2 && bottom.size == 2) { "Must be 2 digits" }
        val numerator = top - bottom
        if (numerator.size != 1) return null
        val denominator = bottom - top
        if (denominator.size != 1) return null
        return Fraction(numerator.first(), (denominator.first()))
    }


fun productSumFactors(max: Int) = sequence {

    val size = log2(max.toDouble()).toInt() + 1
    val array = List(size) { 1 }.toIntArray()

    while (true) {
        var indexOn = array.size - 1
        array[indexOn]++
        var reduced: Int

        while (array.reduce { acc, i -> acc * i }.also { reduced = it } > max) { // for doesn't work
            indexOn--
            if (indexOn < 0) return@sequence
            val nextIndex = ++array[indexOn]
            for (i in indexOn until size) array[i] = nextIndex
        }

        val sum = array.sum()
        val k = size + (reduced - sum)

        yield(FactorResult(k, reduced, array.toList()))
    }
}

fun Int.factors(from: Int = 2, list: ArrayList<Int> = ArrayList()): List<Int> {
    val to = sqrt(this.toDouble()).toInt() + 1 // ceil because of floating point when really should be floor
    for (i in from..to) {
        if (this % i == 0) {
            list.add(i)
            return (this / i).factors(from, list)
        }
    }
    list.add(this)
    return list
}

tailrec fun Long.factors(from: Long = 2L, list: ArrayList<Long> = ArrayList()): List<Long> {
    val to = ceil(sqrt(this.toDouble())) // ceil because of floating point when really should be floor
    for (i in from..to.toLong()) {
        if (this % i == 0L) {
            list.add(i)
            return (this / i).factors(from, list)
        }
    }
    list.add(this)
    return list
}
fun Iterable<Int>.product() = reduce { acc, i -> acc*i }


val Int.isPalindrome: Boolean
    get() {
        val string = this.toString()
        val midpoint = string.length / 2

        val midpointEnd = midpoint
        val midpointStart = if (string.length % 2 == 0) midpoint else midpoint + 1

        val beginning = string.substring(0 until midpointEnd)
        val end = string.substring(midpointStart until string.length).reversed()
        return beginning == end
    }
