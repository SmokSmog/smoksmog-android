package pl.malopolska.smoksmog;


import org.junit.Before;
import org.junit.Test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import pl.malopolska.smoksmog.model.Description;
import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test API with mocks
 */
public class ApiTest {

    private MockWebServer server;
    private Api api;
    private String endpoint;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

        final SmokSmog smokSmog = new SmokSmog(Locale.ENGLISH, server.url("/").toString());

        endpoint = smokSmog.getEndpoint();
        api = smokSmog.getApi();
    }

    @Test
    public void testStations() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStations.json")));

        // when
        List<Station> result = api.stations().toBlocking().first();

        // then
        assertThat(result).hasSize(50);
        final Station station = findById(result, 8);
        assertThat(station.getId()).isEqualTo(8);
        assertThat(station.getName()).isEqualTo("Olkusz");
        assertThat(station.getLatitude()).isEqualTo(50.27756900f);
        assertThat(station.getParticulates()).isNullOrEmpty();
    }

    private Station findById(Collection<Station> collection, long id) {
        for (Station station : collection) {
            if (station.getId() == id) {
                return station;
            }
        }
        throw new IllegalStateException("Station " + id + "not found");
    }

    @Test
    public void testStationsList() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStations.json")));

        // when
        List<Station> result = api.stations().toBlocking().first();
        List<String> stations = new ArrayList<>();

        for (Station station : result) {
            stations.add(station.getName());
        }

        Collections.sort(stations, Collator.getInstance());

        // then
        StringBuilder stringBuilder = new StringBuilder();

        for (String station : stations) {
            stringBuilder.append("* ").append(station).append("\n");
        }
        System.out.print(stringBuilder.toString());
    }

    @Test
    public void testStation() throws Exception {

        // given
        final int stationId = 4;
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStation.json")));

        // when
        Station result = api.station(stationId).toBlocking().first();

        // then
        RecordedRequest request = server.takeRequest();

        final HttpUrl httpUrl = HttpUrl.parse(endpoint + request.getPath());
        final List<String> encodedPathSegments = httpUrl.encodedPathSegments();

        assertThat(encodedPathSegments.get(encodedPathSegments.size() - 1)).isEqualTo("" + stationId);

        final List<Particulate> particulates = result.getParticulates();

        assertThat(result.getId()).isEqualTo(4);
        assertThat(particulates.size()).isEqualTo(4);
    }

    @Test
    public void testStationGeo() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStation.json")));
        final double latitude = 50.234234f;
        final double longitude = 30.123424f;

        // when
        Station result = api.stationByLocation(latitude, longitude).toBlocking().first();

        // then
        RecordedRequest request = server.takeRequest();
        final HttpUrl httpUrl = HttpUrl.parse(endpoint + request.getPath());
        final List<String> encodedPathSegments = httpUrl.encodedPathSegments();

        assertThat(encodedPathSegments.get(encodedPathSegments.size() - 2)).isEqualTo("" + latitude);
        assertThat(encodedPathSegments.get(encodedPathSegments.size() - 1)).isEqualTo("" + longitude);

        final List<Particulate> particulates = result.getParticulates();

        assertThat(result.getId()).isEqualTo(4);
        assertThat(particulates.size()).isEqualTo(4);
    }

    @Test
    public void testStationHistory() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStationHistory.json")));
        final long stationId = 8;

        // when
        Station result = api.stationHistory(stationId).toBlocking().first();

        // then
        RecordedRequest request = server.takeRequest();
        final HttpUrl httpUrl = HttpUrl.parse(endpoint + request.getPath());
        final List<String> encodedPathSegments = httpUrl.encodedPathSegments();

        assertThat(encodedPathSegments.get(encodedPathSegments.size() - 2)).isEqualTo("" + stationId);

        final List<Particulate> particulates = result.getParticulates();

        assertThat(result.getId()).isEqualTo(8);
        assertThat(particulates.size()).isEqualTo(2);
        assertThat(particulates.get(0).getValues().size()).isEqualTo(14);
        assertThat(particulates.get(0).getValues().get(0).getValue()).isEqualTo(18.12f);
    }

    @Test
    public void testStationHistoryByLocation() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseStationHistory.json")));
        final double latitude = 50.234234f;
        final double longitude = 30.123424f;

        // when
        Station result = api.stationHistoryByLocation(latitude, longitude).toBlocking().first();

        // then
        RecordedRequest request = server.takeRequest();
        final HttpUrl httpUrl = HttpUrl.parse(endpoint + request.getPath());
        final List<String> encodedPathSegments = httpUrl.encodedPathSegments();
        final int size = encodedPathSegments.size();

        assertThat(encodedPathSegments.get(size - 3)).isEqualTo("" + latitude);
        assertThat(encodedPathSegments.get(size - 2)).isEqualTo("" + longitude);

        final List<Particulate> particulates = result.getParticulates();

        assertThat(result.getId()).isEqualTo(8);
        assertThat(particulates.size()).isEqualTo(2);
        assertThat(particulates.get(0).getValues().size()).isEqualTo(14);
        assertThat(particulates.get(0).getValues().get(0).getValue()).isEqualTo(18.12f);
    }

    @Test
    public void testParticulateDescription() throws Exception {

        // given
        server.enqueue(new MockResponse().setBody(TestUtils.readFromResources("/responseParticulateDescription.json")));
        long particulateId = 3;

        // when
        Description result = api.particulateDescription(particulateId).toBlocking().first();

        // then
        RecordedRequest request = server.takeRequest();
        final HttpUrl httpUrl = HttpUrl.parse(endpoint + request.getPath());
        final List<String> encodedPathSegments = httpUrl.encodedPathSegments();
        final int size = encodedPathSegments.size();

        assertThat(encodedPathSegments.get(size - 2)).isEqualTo("" + particulateId);
        assertThat(result.getDesc()).isNotNull();
    }
}