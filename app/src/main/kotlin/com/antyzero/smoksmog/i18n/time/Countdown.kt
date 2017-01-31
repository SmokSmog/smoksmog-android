package com.antyzero.smoksmog.i18n.time

interface Countdown {

    operator fun get(givenSeconds: Int): String
}
