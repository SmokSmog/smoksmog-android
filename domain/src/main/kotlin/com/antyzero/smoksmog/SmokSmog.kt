package com.antyzero.smoksmog

import com.antyzero.smoksmog.location.Location
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.model.Station
import rx.Observable

class SmokSmog(val api: Api, val storage: PersistentStorage, val locationProvider: LocationProvider) {

    fun collectData(): Observable<Pair<Item, Station>> = Observable.merge(storage.fetchAll().map { collectDataForItem(it) }.toList())

    private fun collectDataForItem(item: Item): Observable<Pair<Item, Station>> {
        return when (item) {
            is Item.Station -> collectDataForStation(item)
            is Item.Nearest -> nearestStation()
        }.zipWith(Observable.just(item)) {
            station, item ->
            item to station
        }.limit(1)
    }

    private fun collectDataForStation(itemStation: Item.Station) = api.station(itemStation.id)

    fun nearestStation(): Observable<Station> {
        return locationProvider.location()
                .filter { it is Location.Position }
                .cast(Location.Position::class.java)
                .flatMap { api.stationByLocation(it.coordinates.first, it.coordinates.second) }
                .limit(1)
    }
}