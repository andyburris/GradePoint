package com.andb.apps.aspen.util

fun Double.toDecimalString(places: Int, round: Boolean = true): String{
    val string = this.toString()
    val point = string.indexOf('.')

    if (point == -1) return "$string.${addZeroes(places)}"

    val after = string.substring(point + 1)
    return when{
        after.length < places -> string + addZeroes(places - after.length)
        after.length == places -> string
        else -> {
            val roundDigit = after[places].toString().toInt()
            val trimmed = after.substring(0 until places)
            val last = trimmed.last().toString().toInt() + if (round && roundDigit >= 5) 1 else 0
            return string.substring(0..point) + trimmed.dropLast(1) + last
        }
    }
}

fun String.trimTrailingZeroes() = this.dropLastWhile { it == '0' }.dropLastWhile { it == '.' }
fun Double.trimTrailingZeroes() = this.toString().trimTrailingZeroes()

fun addZeroes(amount: Int): String{
    return (0 until amount).joinToString(separator = "") { "0" }
}