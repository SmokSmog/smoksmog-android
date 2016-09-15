package com.antyzero.smoksmog.mock;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.SmokSmog;

/**
 *
 */
public class MockSmokSmog extends SmokSmog {

    private final MockApi mockApi;

ó    public MockSmokSmog() {
        super();
        mockApi = new MockApi(getGson());
    }

    @Override
    public Api getApi() {
        return mockApi;
    }
}
