package com.antyzero.smoksmog.time

interface Countdown {

    operator fun get(givenSeconds: Int): String
}
