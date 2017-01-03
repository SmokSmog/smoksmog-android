package com.antyzero.smoksmog.storage

import com.antyzero.smoksmog.storage.model.Item
import com.antyzero.smoksmog.storage.model.Item.Station
import com.antyzero.smoksmog.storage.model.Module.AirQualityIndex
import com.antyzero.smoksmog.storage.model.Module.AirQualityIndex.Type.POLISH
import com.antyzero.smoksmog.storage.model.Module.Measurements
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File

class StorageTest {

    private lateinit var storage: Storage

    @Before
    fun setUp() {
        storage = JsonFileStorage()
    }

    @Test
    fun addById() {
        val id = 3L

        storage.addStation(id)

        assertThat(storage.fetchAll()).hasSize(1)
        assertThat(storage.fetchAll()[0].id).isEqualTo(id)
    }

    @Test
    fun remove() {
        with(storage) {
            addStation(1)
            addStation(2)
        }

        storage.removeById(1)

        assertThat(storage.fetchAll()).hasSize(1)
        assertThat(storage.fetchAll()[0].id).isEqualTo(2)
    }

    @Test
    fun update() {
        storage.add(Station(3))

        storage.update(3, Station(4).apply {
            modules.add(Measurements())
        })

        val stations = storage.fetchAll()
        assertThat(stations).hasSize(1)
        assertThat(stations[0].id).isEqualTo(3)
        assertThat(stations[0].modules).hasSize(1)
        assertThat(stations[0].modules.first()).isInstanceOf(Measurements::class.java)
    }

    @Test
    fun reference() {
        val list = storage.fetchAll()

        storage.addStation(1)
        storage.addStation(2)
        storage.update(1, Station())

        assertThat(list).hasSize(2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidStationIdValues() {
        storage.addStation(0)
    }

    @Test
    fun addNearestAtTheBeginning() {
        with(storage){
            addStation(1)
            addStation(2)
            addStation(3)
        }
        val nearest = Item.Nearest()

        storage.add(nearest)

        assertThat(storage.fetchAll()[0].id).isEqualTo(nearest.id)
        assertThat(storage.fetchAll()).hasSize(4)
    }

    @Test
    fun duplicatesPrevented() {
        storage.addStation(1)
        storage.addStation(1)

        assertThat(storage.fetchAll()).hasSize(1)
    }

    @Test
    fun persistenceNotCleaned() {
        val file = File.createTempFile("pref", "json")
        val storageOne: PersistentStorage = JsonFileStorage(file)
        storageOne.add(Station(1, mutableSetOf(
                AirQualityIndex(POLISH),
                Measurements()
        )))
        storageOne.addStation(2)
        storageOne.update(2, Station(modules = mutableSetOf(
                Measurements()
        )))

        val storageTwo = JsonFileStorage(file)

        assertThat(storageTwo.fetchAll()).hasSize(2)
        assertThat(storageTwo.fetchAll()[0].id).isEqualTo(1)
        assertThat(storageTwo.fetchAll()[0].modules).hasSize(2)
        assertThat(storageTwo.fetchAll()[0].modules.toList()[0]).isInstanceOf(AirQualityIndex::class.java)
        assertThat(storageTwo.fetchAll()[0].modules.toList()[1]).isInstanceOf(Measurements::class.java)
        assertThat(storageTwo.fetchAll()[1].id).isEqualTo(2)
        assertThat(storageTwo.fetchAll()[1].modules).hasSize(1)
        assertThat(storageTwo.fetchAll()[1].modules.toList()[0]).isInstanceOf(Measurements::class.java)
    }

    @Test
    fun persistenceCleaned() {
        val file = File.createTempFile("pref", "json")
        val storageOne: PersistentStorage = JsonFileStorage(file)
        storageOne.add(Station(1, mutableSetOf(
                AirQualityIndex(POLISH),
                Measurements()
        )))
        storageOne.clear()

        val storageTwo = JsonFileStorage(file)

        assertThat(storageTwo.fetchAll()).hasSize(0)
    }
}
