package com.antyzero.smoksmog.mock;

import com.antyzero.smoksmog.network.NetworkModule;

import org.mockito.Mockito;

import pl.malopolska.smoksmog.RestClient;

public class MockNetworkModule extends NetworkModule {

    public RestClient provideSmokSmog() {
        return Mockito.mock(RestClient.class);
    }
}
