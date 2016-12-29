package com.antyzero.smoksmog

import com.antyzero.smoksmog.location.Location
import com.antyzero.smoksmog.location.Location.Position
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.storage.JsonFileStorage
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.RestClient
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.observers.TestSubscriber

@Suppress("UNCHECKED_CAST")
class SmokSmogTest {

    @Test
    fun nearestStation() {
        val latitude = 49.617454
        val longitude = 20.715333
        val testSubscriber = TestSubscriber<Station>()
        val locationProvider = mock<LocationProvider> {
            on { location() } doReturn Position(latitude to longitude).observable() as Observable<Location>
        }
        val api = mock<Api> {
            on { stationByLocation(latitude, longitude) } doReturn Station(
                    6, "Nowy Sącz", latitude = latitude.toFloat(), longitude = longitude.toFloat()).observable()
        }
        val smokSmog = SmokSmog(api, mock<PersistentStorage> {}, locationProvider)

        smokSmog.nearestStation().subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(1)
        assertThat(testSubscriber.onNextEvents[0].id).isEqualTo(6)
    }

    @Test
    fun locationUnknown() {
        val testSubscriber = TestSubscriber<Station>()
        val locationProvider = mock<LocationProvider> {
            on { location() } doReturn Location.Unknown().observable() as Observable<Location>
        }
        val smokSmog = SmokSmog(mock <Api> { }, mock<PersistentStorage> { }, locationProvider)

        smokSmog.nearestStation().subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(0)
    }
}

private fun <T> T.observable() = Observable.just(this)