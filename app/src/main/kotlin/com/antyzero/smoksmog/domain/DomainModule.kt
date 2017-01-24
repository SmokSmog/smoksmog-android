package com.antyzero.smoksmog.domain

import android.content.Context
import com.antyzero.smoksmog.SmokSmog
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.location.SimpleLocationProvider
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.storage.JsonFileStorage
import com.antyzero.smoksmog.storage.PersistentStorage
import com.antyzero.smoksmog.storage.model.Item
import dagger.Module
import dagger.Provides
import pl.malopolska.smoksmog.Api
import pl.malopolska.smoksmog.RestClient
import rx.schedulers.Schedulers
import java.io.File
import javax.inject.Singleton

@Singleton
@Module
class DomainModule {

    @Provides
    @Singleton
    internal fun provideSmokSmog(storage: PersistentStorage, locationProvider: LocationProvider, api: Api, settingsHelper: SettingsHelper): SmokSmog {

        return SmokSmog(api, storage, locationProvider).apply {

            // Conversion is temporary
            if (settingsHelper.stationIdList.isEmpty().not()) {
                for (id in settingsHelper.stationIdList) {
                    this.storage.add(Item.Station(id))
                }
                settingsHelper.preferences.edit().clear().apply()
                settingsHelper.stationIdList.clear()
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideApi(context: Context): Api {
        @Suppress("DEPRECATION")
        return RestClient.Builder(context.resources.configuration.locale).apply {
            scheduler = Schedulers.io()
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideLocationStorage(context: Context): PersistentStorage {
        return JsonFileStorage(File(context.filesDir, "storage.json"))
    }

    @Provides
    @Singleton
    internal fun provideLocationProvider(): LocationProvider {
        return SimpleLocationProvider()
    }
}