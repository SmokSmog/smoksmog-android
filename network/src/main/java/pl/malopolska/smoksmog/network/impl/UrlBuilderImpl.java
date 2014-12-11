package pl.malopolska.smoksmog.network.impl;

import java.util.Locale;

import pl.malopolska.smoksmog.network.UrlBuilder;

/**
 * TODO check interface comments for implementation details
 */
public final class UrlBuilderImpl implements UrlBuilder {

    private final String baseUrl;
    private final String locale;

    /**
     *
     *
     * @param baseUrl
     */
    private UrlBuilderImpl(String baseUrl, Locale locale){
        this.baseUrl = baseUrl;
        this.locale = locale.getISO3Language();
    }

    /**
     * Factory method
     *
     * @return UrlBuilder implementation
     */
    public static UrlBuilder create(String baseUrl, Locale locale){
        return new UrlBuilderImpl(baseUrl, locale);
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
