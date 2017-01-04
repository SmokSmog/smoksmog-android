package com.antyzero.smoksmog.location

import rx.Observable
import rx.subjects.BehaviorSubject

class SimpleLocationProvider(initialLocation: Location = Location.Unknown()) : LocationProvider {

    val locationSubject: BehaviorSubject<Location> = BehaviorSubject.create(initialLocation)

    override fun location(): Observable<Location> = locationSubject
}