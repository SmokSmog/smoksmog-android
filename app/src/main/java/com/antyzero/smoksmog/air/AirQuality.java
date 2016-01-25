package com.antyzero.smoksmog.air;

import android.support.annotation.ColorRes;

import com.antyzero.smoksmog.R;

/**
 *
 */
public enum AirQuality implements ValueCheck {

    VERY_GOOD( R.color.airQualityVeryGood ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value <= 1;
        }
    },

    GOOD( R.color.airQualityGood ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value > 1 && value <= 3;
        }
    },

    MODERATE( R.color.airQualityModerate ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value > 3 && value <= 5;
        }
    },

    SUFFICIENT( R.color.airQualitySufficient ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value > 5 && value <= 7;
        }
    },

    BAD( R.color.airQualityBad ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value > 7 && value <= 10;
        }
    },

    VERY_BAD( R.color.airQualityVeryBad ) {
        @Override
        public boolean isValueInRange( double value ) {
            return value > 10;
        }
    };

    private final int colorId;

    AirQuality( @ColorRes int colorId ) {
        this.colorId = colorId;
    }

    public static final AirQuality findByValue( double value ) {

        for ( AirQuality airQuality : values() ) {

            if ( airQuality.isValueInRange( value ) ) {
                return airQuality;
            }
        }

        throw new IllegalStateException( "Unable to find AirQuality for given value (" + value + ")" );
    }
}
