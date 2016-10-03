package com.antyzero.smoksmog.mock;

import com.antyzero.smoksmog.network.NetworkModule;

import pl.malopolska.smoksmog.SmokSmog;

public class MockNetworkModule extends NetworkModule {

    public SmokSmog provideSmokSmog() {
        return new MockSmokSmog();
    }
}
