package com.antyzero.smoksmog


object StringRandom {

    private const val LOWER_CASE_A = 97

    fun random(length: Int = 8) = (1..length).fold("") { previous, position -> previous + "$length" }
}