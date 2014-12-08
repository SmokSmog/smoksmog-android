package pl.malopolska.smoksmog.network.impl;

import pl.malopolska.smoksmog.network.UrlBuilder;

/**
 * TODO check interface comments for implementation details
 */
public class UrlBuilderImpl implements UrlBuilder {

    @Override
    public String stations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String station(long stationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String station(float latitude, float longitude) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String stationHistory(long stationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String stationHistory(float latitude, float longitude) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String particulates(long particleId) {
        throw new UnsupportedOperationException();
    }
}
