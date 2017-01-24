package com.antyzero.smoksmog

import java.util.*


class StringRandom(seed: Long = System.nanoTime()) {

    private val LOWER_CASE_A = 97
    private val random = Random(seed)

    fun random(length: Int = 8) = (1..length).fold("") {
        previous, position ->
        previous + (LOWER_CASE_A + random.nextInt(22)).toChar()
    }
}