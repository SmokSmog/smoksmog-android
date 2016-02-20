package pl.malopolska.smoksmog.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iwopolanski on 25.01.16.
 */
public class ParticulateEnumTest {

    @Test
    public void testNonSupportedId() throws Exception {

        // given
        long id = -34;

        // when
        ParticulateEnum result = ParticulateEnum.findById( -34 );

        // then
        assertThat( result ).isNotNull();
        assertThat( result ).isEqualTo( ParticulateEnum.UNKNOWN );
        assertThat( result.getId() ).isEqualTo( id );

        System.out.println( result );
    }
}