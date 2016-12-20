package com.antyzero.smoksmog.mock;

import android.support.annotation.NonNull;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.SmokSmog;

/**
 *
 */
class MockSmokSmog extends SmokSmog {

    private final MockApi mockApi;

    MockSmokSmog() {
        super();
        mockApi = new MockApi(SmokSmog.Companion.createGson());
    }

    @NonNull
    @Override
    public Api getApi() {
        return mockApi;
    }
}
