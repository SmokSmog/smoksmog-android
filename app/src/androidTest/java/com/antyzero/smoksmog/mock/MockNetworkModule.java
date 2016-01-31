package com.antyzero.smoksmog.mock;

import android.content.Context;

import com.antyzero.smoksmog.network.NetworkModule;

import pl.malopolska.smoksmog.SmokSmog;


public class MockNetworkModule extends NetworkModule {

    @Override
    public SmokSmog provideSmokSmog( Context context ) {
        return new MockSmokSmog( createBuilder( context ) );
    }
}
