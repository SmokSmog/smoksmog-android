package smoksmog.air

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class AirQualityIndexTest {

    @Test
    fun firstRangeCorrectResult() {
        var value = 0f
        do {
            val index = AirQualityIndex.calculateBenzene(value)
            assertThat(index).isLessThan(1f)
            value += 0.001f
        } while (value < 5f)
    }

    @Test
    fun secondRangeCorrectResult() {
        var value = 5f
        do {
            val index = AirQualityIndex.calculateBenzene(value)
            assertThat(index).isLessThan(7f)
            value += 0.001f
        } while (value < 20f)
    }

    @Test
    fun thirdRangeCorrectResult() {
        var value = 20f
        do {
            val index = AirQualityIndex.calculateBenzene(value)
            assertThat(index).isLessThan(10f)
            value += 0.001f
        } while (value < 50f)
    }

}