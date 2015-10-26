package pl.malopolska.smoksmog;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.malopolska.smoksmog.model.Particulate;

/**
 * Created by iwopolanski on 24.10.2015.
 */
public class ParticulateTest {

    private Api api;
    private String endpoint;

    @Before
    public void setUp() throws Exception {

        final SmokSmog smokSmog = new SmokSmog.Builder( "http://api.smoksmog.jkostrz.name/", Locale.ENGLISH ).build();

        endpoint = smokSmog.getEndpoint();
        api = smokSmog.getApi();
    }

    @Test
    public void testCollect() throws Exception {

        Map<Long, Particulate> map = new HashMap<>(  );

        for( int i = 0; i < 200; i++) {
            List<Particulate> list = Collections.emptyList();
            try {
                list = api.station( i ).toBlocking().first().getParticulates();
            } catch ( Exception e ) {

            }


            for ( Particulate particulate : list ) {
                map.put( particulate.getId(), particulate );
            }
        }

        String.valueOf( map );
    }
}
