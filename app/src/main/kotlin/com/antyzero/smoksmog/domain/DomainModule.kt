package com.antyzero.smoksmog.domain

import android.content.Context
import android.os.Build
import com.antyzero.smoksmog.BuildConfig
import com.antyzero.smoksmog.SmokSmog
import com.antyzero.smoksmog.i18n.LocaleProvider
import com.antyzero.smoksmog.location.LocationProvider
import com.antyzero.smoksmog.location.SimpleLocationProvider
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.settings.SettingsHelper.Companion.KEY_STATION_ID_LIST
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

    @Suppress("DEPRECATION")
    @Provides
    @Singleton
    internal fun provideSmokSmog(storage: PersistentStorage, locationProvider: LocationProvider, api: Api, settingsHelper: SettingsHelper): SmokSmog {

        return SmokSmog(api, storage, locationProvider).apply {

            // Conversion is temporary
            if (settingsHelper.stationIdList.isEmpty().not()) {
                for (id in settingsHelper.stationIdList) {
                    this.storage.add(Item.Station(id))
                }
                if (settingsHelper.nearestStation) {
                    this.storage.add(Item.Nearest())
                }
                settingsHelper.preferences.edit().remove(KEY_STATION_ID_LIST).apply() // remove storage
                settingsHelper.stationIdList.clear() // remove temp data
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideApi(localeProvider: LocaleProvider): Api {
        return RestClient.Builder(localeProvider.get()).apply {
            scheduler = Schedulers.io()
            requestCustomizer = {
                it.addHeader("User-Agent", "SmokSmog/%s (Android %s)".format(
                        BuildConfig.VERSION_NAME, Build.VERSION.RELEASE))
            }
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