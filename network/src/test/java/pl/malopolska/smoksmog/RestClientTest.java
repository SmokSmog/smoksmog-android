package pl.malopolska.smoksmog;

import org.junit.Test;

import java.util.Locale;

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
}