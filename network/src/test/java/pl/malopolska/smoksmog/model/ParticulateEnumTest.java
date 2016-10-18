package pl.malopolska.smoksmog.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParticulateEnumTest {

    @Test
    public void testNonSupportedId() throws Exception {

        // given
        long id = -34;

        // when
        ParticulateEnum result = ParticulateEnum.Companion.findById(-34);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ParticulateEnum.UNKNOWN);
    }
}