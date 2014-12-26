package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogAPITest extends TestCase {

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

    public void testStation(){

        StationParticulates station = smokSmogAPI.station( 4 );

        Collection<ParticulateDetails> particulates = station.getParticulates();

        assertThat( station ).isNotNull();
        assertThat( particulates ).isNotNull().isNotEmpty();

        for( ParticulateDetails particulate : particulates ){
            assertThat( particulate.getDate() ).isNotNull();
        }
    }

    public void testStationHistory() {
        

    }
}