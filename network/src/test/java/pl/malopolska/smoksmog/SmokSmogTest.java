package pl.malopolska.smoksmog;

import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogTest {

    @Test
    public void testCreateServerUrlWithLocale() throws Exception {

        // given
        Locale locale = Locale.ENGLISH;
        SmokSmog smokSmog = new SmokSmog(locale);

        // when
        String result = smokSmog.getEndpoint();

        // then
        assertThat(result).isEqualTo("http://api.smoksmog.jkostrz.name/" + locale.getLanguage() + "/");
    }
}