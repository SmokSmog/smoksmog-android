package pl.malopolska.smoksmog.utils


import pl.malopolska.smoksmog.model.Station
import rx.Observable

class StationUtils private constructor() {

    init {
        throw IllegalAccessError("Utils class")
    }

    companion object {

        fun convertStationsToIdsList(stationList: List<Station>): List<Long> {
            return Observable.from(stationList).map { station -> station.id }.toList().toBlocking().first()
        }

        fun convertStationsToIdsArray(stations: Collection<Station>): LongArray {
            val result = LongArray(stations.size)
            var i = 0
            for ((id) in stations) {
                result[i] = id
                i++
            }
            return result
        }

        fun findClosest(stations: Collection<Station>, latitude: Double, longitude: Double): Station {

            var closestStation: Station = stations.first()
            var distance = java.lang.Double.MAX_VALUE

            for (station in stations) {

                val calculatedDistance = distanceInRadians(
                        station.latitude.toDouble(), station.longitude.toDouble(),
                        latitude, longitude)

                if (calculatedDistance < distance) {
                    closestStation = station
                    distance = calculatedDistance
                }
            }

            return closestStation
        }

        private fun distanceInRadians(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double): Double {

            val x1 = Math.toRadians(latitude1)
            val y1 = Math.toRadians(longitude1)
            val x2 = Math.toRadians(latitude2)
            val y2 = Math.toRadians(longitude2)

            // great circle distance in radians
            return Math.acos(Math.sin(x1) * Math.sin(x2) + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2))
        }
    }
}
