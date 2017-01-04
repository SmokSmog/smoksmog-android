package com.antyzero.smoksmog

import com.antyzero.smoksmog.location.Location
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.model.Station
import rx.Observable

class SmokSmog(val api: Api, val storage: PersistentStorage, val locationProvider: LocationProvider) {

    fun collectData(): Observable<Pair<Item, Station>> = Observable.from(storage.fetchAll())
            .flatMap { collectDataForItem(it) }

    fun collectDataForItem(item: Item): Observable<Pair<Item, Station>> = when (item) {
        is Item.Station -> collectDataForStation(item)
        is Item.Nearest -> nearestStation()
        else -> throw IllegalStateException("Unsupported item type $item")
    }.zipWith(Observable.just(item)) {
        station, item ->
        item to station
    }.limit(1)

    private fun collectDataForStation(itemStation: Item.Station) = api.station(itemStation.id)

    // TODO placeholder needed, we need to show that there should be near station
    fun nearestStation(): Observable<Station> = locationProvider.location()
            .filter { it is Location.Position }
            .cast(Location.Position::class.java)
            .flatMap { api.stationByLocation(it.coordinates.first, it.coordinates.second) }
            .limit(1)
}