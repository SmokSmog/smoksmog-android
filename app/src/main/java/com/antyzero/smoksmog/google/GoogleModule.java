package com.antyzero.smoksmog.google;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleModule {

    @Provides
    public GoogleApiClient provideGoogleApiClient( Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks ) {

        return new GoogleApiClient.Builder( context )
                .addConnectionCallbacks( connectionCallbacks )
                .addApi( LocationServices.API )
                .build();
    }
}
