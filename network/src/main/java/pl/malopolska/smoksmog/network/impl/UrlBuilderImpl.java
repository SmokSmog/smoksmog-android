package pl.malopolska.smoksmog.network.impl;

import android.net.Uri;

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
        this.locale = locale.getLanguage();
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
        return builder().appendPath("stations").build().toString();
    }

    @Override
    public String station(long stationId) {
        return builder()
                .appendPath("stations")
                .appendPath(String.valueOf(stationId))
                .build().toString();
    }

    @Override
    public String station(float latitude, float longitude) {
        return builder()
                .appendPath("stations")
                .appendPath(String.valueOf(latitude))
                .appendPath(String.valueOf(longitude))
                .build().toString();
    }

    @Override
    public String stationHistory(long stationId) {
        return builder()
                .appendPath("stations")
                .appendPath(String.valueOf(stationId))
                .appendPath("history")
                .build().toString();
    }

    @Override
    public String stationHistory(float latitude, float longitude) {
        return builder()
                .appendPath("stations")
                .appendPath(String.valueOf(latitude))
                .appendPath(String.valueOf(longitude))
                .appendPath("history")
                .build().toString();
    }

    @Override
    public String particulates(long particleId) {
        return builder()
                .appendPath("particulates")
                .appendPath(String.valueOf(particleId))
                .appendPath("desc")
                .build().toString();
    }

    private Uri.Builder builder(){
        return Uri.parse(baseUrl).buildUpon().appendPath(locale);
    }
}
