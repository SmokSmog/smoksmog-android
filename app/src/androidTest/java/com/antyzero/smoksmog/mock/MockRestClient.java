package com.antyzero.smoksmog.mock;

import android.support.annotation.NonNull;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.RestClient;

/**
 *
 */
class MockRestClient extends RestClient {

    private final MockApi mockApi;

    MockRestClient() {
        super();
        mockApi = new MockApi(RestClient.Companion.createGson());
    }

    @NonNull
    @Override
    public Api getApi() {
        return mockApi;
    }
}
