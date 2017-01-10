package com.antyzero.smoksmog

import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.storage.JsonFileStorage
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.RestClient
import pl.malopolska.smoksmog.model.Station
import rx.observers.TestSubscriber
import java.io.File

class DataCollectionTest {

    @Mock
    lateinit private var locationProvider: LocationProvider
    lateinit private var storage: PersistentStorage
    lateinit private var smokSmog: SmokSmog

    private var api: Api = RestClient.Builder().build()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val file = File.createTempFile(StringRandom().random(8), "json").apply { delete() }
        storage = JsonFileStorage(file)
        smokSmog = SmokSmog(api, storage, locationProvider)
    }

    @After
    fun tearDown() {
        storage.clear()
    }

    @Test
    fun collectForSingle() {
        val testSubscriber = TestSubscriber<Pair<Item, Station>>()

        smokSmog.collectDataForItem(Item.Station(13)).subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun fetchAll() {
        val testSubscriber = TestSubscriber<Pair<Item, Station>>()
        smokSmog.storage.addStation(13)
        smokSmog.storage.addStation(17)

        smokSmog.collectData().subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(2)
    }
}