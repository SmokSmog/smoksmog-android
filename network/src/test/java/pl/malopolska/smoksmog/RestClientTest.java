package pl.malopolska.smoksmog;

import org.junit.Test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import pl.malopolska.smoksmog.model.Station;

import static org.assertj.core.api.Assertions.assertThat;

public class RestClientTest {

    @Test
    public void testCreateServerUrlWithLocale() throws Exception {

        // given
        Locale locale = Locale.ENGLISH;
        RestClient restClient = new RestClient.Builder(locale).build();

        // when
        String result = restClient.getEndpoint();

        // then
        assertThat(result).isEqualTo("http://api.smoksmog.jkostrz.name/" + locale.getLanguage() + "/");
    }

    @Test
    public void stations() throws Exception {

        List<Station> stations = new RestClient.Builder().build().stations().toBlocking().first();
        List<String> names = new ArrayList<>();

        for (Station station : stations) {
            names.add(station.getName());
        }

        Collections.sort(names, Collator.getInstance());

        for(String name : names){
            System.out.print(name + ", ");
        }
    }
}