package com.antyzero.smoksmog.location

import rx.Observable

interface LocationProvider {

    fun location(): Observable<Location>
}

sealed class Location {

    class Position(val coordinates: Pair<Double, Double>) : Location()

    class Unknown : Location()
}