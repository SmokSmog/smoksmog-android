package smoksmog.air

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.StringRes

import smoksmog.R

/**

 */
enum class AirQuality constructor(@ColorRes val colorResId: Int, @StringRes val titleResId: Int) : ValueCheck {

    VERY_GOOD(R.color.airQualityVeryGood, R.string.airQualityVeryGood) {
        override fun isValueInRange(value: Double): Boolean {
            return value <= 1
        }
    },

    GOOD(R.color.airQualityGood, R.string.airQualityGood) {
        override fun isValueInRange(value: Double): Boolean {
            return value > 1 && value <= 3
        }
    },

    MODERATE(R.color.airQualityModerate, R.string.airQualityModerate) {
        override fun isValueInRange(value: Double): Boolean {
            return value > 3 && value <= 5
        }
    },

    SUFFICIENT(R.color.airQualitySufficient, R.string.airQualitySufficient) {
        override fun isValueInRange(value: Double): Boolean {
            return value > 5 && value <= 7
        }
    },

    BAD(R.color.airQualityBad, R.string.airQualityBad) {
        override fun isValueInRange(value: Double): Boolean {
            return value > 7 && value <= 10
        }
    },

    VERY_BAD(R.color.airQualityVeryBad, R.string.airQualityVeryBad) {
        override fun isValueInRange(value: Double): Boolean {
            return value > 10
        }
    };

    fun getTitle(context: Context): CharSequence {
        return context.getString(titleResId)
    }

    fun getColor(context: Context): Int {
        // Deprecated since 23 API, quite fresh
        @Suppress("DEPRECATION")
        return context.resources.getColor(colorResId)
    }

    companion object {

        fun findByValue(value: Double): AirQuality {
            values().filter { it.isValueInRange(value) }.forEach { return it }
            throw IllegalStateException("Unable to find AirQuality for given value ($value)")
        }
    }
}
