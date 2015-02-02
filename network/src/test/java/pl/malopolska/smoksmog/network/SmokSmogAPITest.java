package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Locale;

import pl.malopolska.smoksmog.network.model.HistoryValue;
import pl.malopolska.smoksmog.network.model.ParticulateDescription;
import pl.malopolska.smoksmog.network.model.ParticulateDetails;
import pl.malopolska.smoksmog.network.model.ParticulateHistory;
import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.network.model.StationParticulates;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogAPITest extends TestCase {

    private static final int STATION_ID = 4;
    public static final float LATITUDE = 50.061389f;
    public static final float LONGITUDE = 19.938333f;

    private SmokSmogAPI smokSmogAPI;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        smokSmogAPI = SmokSmogAPIImpl.create(
                "http://api.smoksmog.jkostrz.name/", new Locale("pl"));
    }

    public void testStations() {

        Collection<StationLocation> stations = smokSmogAPI.stations();

        assertThat( stations ).isNotNull().isNotEmpty();

        for( StationLocation stationLocation : stations ){
            TestUtils.invokeAllGetters( stationLocation );
        }
    }

    public void testStationById() {

        StationParticulates station = smokSmogAPI.station( STATION_ID );

        checkStationDetails( station );
    }

    public void testStationByLocation() {

        StationParticulates station = smokSmogAPI.station( LATITUDE, LONGITUDE );

        checkStationDetails( station );
    }

    public void testStationHistoryById() {

        StationHistory station = smokSmogAPI.stationHistory( STATION_ID );

        checkStationHistory( station );
    }

    public void testStationHistoryByLocation() {

        StationHistory station = smokSmogAPI.stationHistory( LATITUDE, LONGITUDE );

        checkStationHistory( station );
    }

    public void testParticulate() {

        ParticulateDescription particulateDescription = smokSmogAPI.particulate( 1 );

        assertThat( particulateDescription ).isNotNull();

        TestUtils.invokeAllGetters( particulateDescription );
    }

    /**
     * Check station
     *
     * @param station with particulates information
     */
    private void checkStationDetails( StationParticulates station ) {

        TestUtils.invokeAllGetters( station );

        assertThat( station ).isNotNull();

        Collection<ParticulateDetails> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateDetails particulate : particulates ) {
            TestUtils.invokeAllGetters( particulate );
            assertThat( particulate.getDate() ).isNotNull();
        }
    }

    private void checkStationHistory( StationHistory station ) {

        TestUtils.invokeAllGetters( station );

        assertThat( station );

        Collection<ParticulateHistory> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateHistory particulate : particulates ) {

            TestUtils.invokeAllGetters( particulate );

            for ( HistoryValue historyValue : particulate.getValues() ) {

                TestUtils.invokeAllGetters( historyValue );

                assertThat( historyValue.getDate() ).isNotNull();
            }
        }
    }
}