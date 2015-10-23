package com.antyzero.smoksmog;

import android.content.Context;

import com.antyzero.smoksmog.network.NetworkModule;

import org.mockito.Mockito;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.SmokSmog;


public class MockNetworkModule extends NetworkModule {

    public MockNetworkModule() {
        SmokSmog smokSmogMock = Mockito.mock( SmokSmog.class );
        Api apiMock = Mockito.mock( Api.class );

    }

    @Override
    public SmokSmog provideSmokSmog( Context context ) {
        return super.provideSmokSmog( context );
    }
}
