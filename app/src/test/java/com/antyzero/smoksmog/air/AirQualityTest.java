package com.antyzero.smoksmog.air;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AirQualityTest {

    @Test
    public void testValuesAreFound() throws Exception {

        // given
        for ( double value = -6; value < 15; value = value + 0.1 ) {

            // when
            AirQuality airQuality = AirQuality.findByValue( value );

            // then
            assertThat( airQuality ).isNotNull()
                    .overridingErrorMessage( "Unable to find proper AirQuality for %s", value );
        }
    }
/*
    @Test
    public void testAreQualitiesCorrect() throws Exception {

        assertThat( AirQuality.findByValue( 0.5 ) ).isEqualTo( AirQuality.VERY_GOOD );
        assertThat( AirQuality.findByValue( 2 ) ).isEqualTo( AirQuality.GOOD );
        assertThat( AirQuality.findByValue( 4 ) ).isEqualTo( AirQuality.MODERATE);
        assertThat( AirQuality.findByValue( 6 ) ).isEqualTo( AirQuality.SUFFICIENT );
        assertThat( AirQuality.findByValue( 8 ) ).isEqualTo( AirQuality.BAD );
        assertThat( AirQuality.findByValue( 11 ) ).isEqualTo( AirQuality.VERY_BAD );
    }
    */
}