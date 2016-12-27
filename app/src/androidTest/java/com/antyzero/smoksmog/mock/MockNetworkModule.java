package com.antyzero.smoksmog.mock;

import com.antyzero.smoksmog.network.NetworkModule;

import pl.malopolska.smoksmog.RestClient;

public class MockNetworkModule extends NetworkModule {

    public RestClient provideSmokSmog() {
        return new MockRestClient();
    }
}
