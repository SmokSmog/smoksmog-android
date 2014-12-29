package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogAPITest extends TestCase {

    private static final int STATION_ID = 4;
    public static final float LATITUDE = 50.061389f;
    public static final float LONGITUDE = 19.938333f;

    private SmokSmogAPI smokSmogAPI;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        smokSmogAPI = SmokSmogAPICreator.create(
                "http://api.smoksmog.jkostrz.name/", new Locale( "pl" ) );
    }

    public void testStations() {
        assertThat( smokSmogAPI.stations() ).isNotNull().isNotEmpty();
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

        assertThat( particulateDescription );
    }

    /**
     * Check station
     *
     * @param station with particulates information
     */
    private void checkStationDetails( StationParticulates station ) {

        assertThat( station ).isNotNull();

        Collection<ParticulateDetails> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateDetails particulate : particulates ) {
            assertThat( particulate.getDate() ).isNotNull();
        }
    }

    private void checkStationHistory( StationHistory station ) {
        assertThat( station );

        Collection<ParticulateHistory> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateHistory particulate : particulates ) {
            for ( HistoryValue value : particulate.getValues() ) {
                assertThat( value.getDate() ).isNotNull();
            }
        }
    }
}