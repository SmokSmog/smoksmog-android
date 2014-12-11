package pl.malopolska.smoksmog.network.impl;

import pl.malopolska.smoksmog.network.UrlBuilder;

/**
 * TODO check interface comments for implementation details
 */
public final class UrlBuilderImpl implements UrlBuilder {

    /**
     * TODO change as pleased, add parameters etc., but keep private
     */
    private UrlBuilderImpl(){

    }

    /**
     * Factory method
     *
     * @return UrlBuilder implementation
     */
    public static UrlBuilder create(){
        return new UrlBuilderImpl();
    }

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
