package com.antyzero.smoksmog.google

import android.content.Context

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

import dagger.Module
import dagger.Provides

@Module
class GoogleModule(private val connectionCallbacks: GoogleApiClient.ConnectionCallbacks) {

    @Provides
    internal fun provideGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addApi(LocationServices.API)
                .build()
    }
}
