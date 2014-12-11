package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import org.assertj.core.api.StringAssert;

import java.util.Locale;

import pl.malopolska.smoksmog.network.impl.UrlBuilderImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlBuilderTest extends TestCase {

    private static final String BASE_URL = "http://api.some.pl/";
    private static final String LANGUAGE_TAG = "pl";

    private static final String URL_START = BASE_URL + LANGUAGE_TAG + "/";

    private UrlBuilder urlBuilder;

    public void setUp() throws Exception {
        super.setUp();

        urlBuilder = UrlBuilderImpl.create(BASE_URL, new Locale(LANGUAGE_TAG));
    }

    public void testStations() throws Exception {

        assertThat(urlBuilder.stations()).startsWith(URL_START + "stations");
    }

    public void testStation() throws Exception {

        int stationId = 23;

        assertThat(urlBuilder.station(stationId)).startsWith(URL_START + "stations/" + stationId);
    }

    public void testStationLatLng() throws Exception {

        float latitude = 23.123456f;
        float longitude = 1.23f;

        assertThat(urlBuilder.station(latitude, longitude))
                .startsWith(URL_START + "stations/" + latitude + "/" + longitude);
    }

    public void testStationHistory() throws Exception {

        int stationId = 23;

        assertThat(urlBuilder.stationHistory(stationId))
                .startsWith(URL_START + "stations/" + stationId + "/history");

    }

    public void testStationHistoryLatLng() throws Exception {

        float latitude = 23.123456f;
        float longitude = 1.23f;

        assertThat(urlBuilder.stationHistory(latitude, longitude))
                .startsWith(URL_START + "stations/" + latitude + "/" + longitude + "/history");
    }

    public void testParticulates() throws Exception {

        int particleId = 3;

        assertThat(urlBuilder.particulates(particleId))
                .startsWith(URL_START + "particulates/" + particleId + "/desc");
    }
}