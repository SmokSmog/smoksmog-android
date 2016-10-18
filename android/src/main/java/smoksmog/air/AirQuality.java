package smoksmog.air;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import smoksmog.R;

/**
 *
 */
public enum AirQuality implements ValueCheck {

    VERY_GOOD(R.color.airQualityVeryGood, R.string.airQualityVeryGood) {
        @Override
        public boolean isValueInRange(double value) {
            return value <= 1;
        }
    },

    GOOD(R.color.airQualityGood, R.string.airQualityGood) {
        @Override
        public boolean isValueInRange(double value) {
            return value > 1 && value <= 3;
        }
    },

    MODERATE(R.color.airQualityModerate, R.string.airQualityModerate) {
        @Override
        public boolean isValueInRange(double value) {
            return value > 3 && value <= 5;
        }
    },

    SUFFICIENT(R.color.airQualitySufficient, R.string.airQualitySufficient) {
        @Override
        public boolean isValueInRange(double value) {
            return value > 5 && value <= 7;
        }
    },

    BAD(R.color.airQualityBad, R.string.airQualityBad) {
        @Override
        public boolean isValueInRange(double value) {
            return value > 7 && value <= 10;
        }
    },

    VERY_BAD(R.color.airQualityVeryBad, R.string.airQualityVeryBad) {
        @Override
        public boolean isValueInRange(double value) {
            return value > 10;
        }
    };

    private final int colorId;
    private final int nameId;

    AirQuality(@ColorRes int colorId, @StringRes int nameId) {
        this.colorId = colorId;
        this.nameId = nameId;
    }

    public static AirQuality findByValue(double value) {

        for (AirQuality airQuality : values()) {

            if (airQuality.isValueInRange(value)) {
                return airQuality;
            }
        }

        throw new IllegalStateException("Unable to find AirQuality for given value (" + value + ")");
    }

    public CharSequence getTitle(Context context) {
        return context.getString(nameId);
    }

    @StringRes
    public int getTitleResId() {
        return nameId;
    }

    public int getColor(Context context) {
        // Deprecated since 23 API, quite fresh
        //noinspection deprecation
        return context.getResources().getColor(colorId);
    }

    @ColorRes
    public int getColorResId() {
        return colorId;
    }
}
