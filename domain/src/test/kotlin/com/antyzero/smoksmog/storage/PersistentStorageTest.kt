package com.antyzero.smoksmog.storage

import com.antyzero.smoksmog.storage.model.Item
import com.antyzero.smoksmog.storage.model.Module
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.File


class PersistentStorageTest {

    private val storageFile = File.createTempFile("pref", "json")

    fun createStorageInstance() = JsonFileStorage(storageFile)

    @Test
    fun persistenceNotCleaned() {
        val storageOne: PersistentStorage = createStorageInstance()
        storageOne.add(Item.Station(1, mutableSetOf(
                Module.AirQualityIndex(Module.AirQualityIndex.Type.POLISH),
                Module.Measurements()
        )))
        storageOne.addStation(2)
        storageOne.update(2, Item.Station(modules = mutableSetOf(
                Module.Measurements()
        )))

        val storageTwo = createStorageInstance()

        Assertions.assertThat(storageTwo.fetchAll()).hasSize(2)
        Assertions.assertThat(storageTwo.fetchAll()[0].id).isEqualTo(1)
        Assertions.assertThat(storageTwo.fetchAll()[0].modules).hasSize(2)
        Assertions.assertThat(storageTwo.fetchAll()[0].modules.toList()[0]).isInstanceOf(Module.AirQualityIndex::class.java)
        Assertions.assertThat(storageTwo.fetchAll()[0].modules.toList()[1]).isInstanceOf(Module.Measurements::class.java)
        Assertions.assertThat(storageTwo.fetchAll()[1].id).isEqualTo(2)
        Assertions.assertThat(storageTwo.fetchAll()[1].modules).hasSize(1)
        Assertions.assertThat(storageTwo.fetchAll()[1].modules.toList()[0]).isInstanceOf(Module.Measurements::class.java)
    }

    @Test
    fun persistenceCleaned() {
        val storageOne: PersistentStorage = createStorageInstance()
        storageOne.add(Item.Station(1, mutableSetOf(
                Module.AirQualityIndex(Module.AirQualityIndex.Type.POLISH),
                Module.Measurements()
        )))
        storageOne.clear()

        val storageTwo = createStorageInstance()

        Assertions.assertThat(storageTwo.fetchAll()).hasSize(0)
    }
}