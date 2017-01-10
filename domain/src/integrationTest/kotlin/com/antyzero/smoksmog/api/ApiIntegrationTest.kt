package com.antyzero.smoksmog.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.malopolska.smoksmog.RestClient
import pl.malopolska.smoksmog.model.Station
import rx.Observable
import rx.observers.TestSubscriber

class ApiIntegrationTest {

    lateinit private var api: RestClient

    @Before
    fun setUp() {
        api = RestClient.Builder().build()
    }

    @Test
    fun stations() {
        val testSubscriber = TestSubscriber<Station>()

        api.stations()
                .flatMap { Observable.from(it) }
                .subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assertThat(testSubscriber.onNextEvents.size).isGreaterThanOrEqualTo(50) // we should have that much
    }
}