package com.antyzero.smoksmog

import com.antyzero.smoksmog.location.Location
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.model.Page
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.model.Station
import rx.Observable

class SmokSmog(val api: Api, val storage: PersistentStorage, val locationProvider: LocationProvider) {

    fun collectData(): Observable<Page> = Observable.from(storage.fetchAll())
            .flatMap { collectDataForItem(it) }

    fun collectDataForItem(item: Item): Observable<Page> = when (item) {
        is Item.Station -> api.station(item.id)
        is Item.Nearest -> nearestStation()
        else -> throw IllegalStateException("Unsupported item type $item")
    }.zipWith(Observable.just(item)) { station, item -> Page(item, station) }.limit(1)

    fun nearestStation(): Observable<Station> = locationProvider.location()
            .filter { it is Location.Position }
            .cast(Location.Position::class.java)
            .flatMap { api.stationByLocation(it.coordinates.first, it.coordinates.second) }
            .limit(1)
}