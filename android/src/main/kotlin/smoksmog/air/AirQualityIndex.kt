package smoksmog.air

import pl.malopolska.smoksmog.model.Particulate
import pl.malopolska.smoksmog.model.ParticulateEnum.*
import pl.malopolska.smoksmog.model.Station

object AirQualityIndex {

    fun calculate(station: Station): Double {
        return calculate(station.particulates)
    }

    fun calculate(particulate: Particulate): Double = calculate(listOf(particulate))

    fun calculate(particulates: Collection<Particulate>): Double {

        var index = 0.0f

        for (particulate in particulates) {

            var particulateValue = 0.0f

            when (particulate.enum) {
                PM10 -> particulateValue = particulate.value / 20
                PM25 -> particulateValue = particulate.value / 12
                SO2 -> particulateValue = particulate.value / 70
                NO2 -> particulateValue = particulate.value / 40
                CO -> particulateValue = particulate.value / 2000
                O3 -> particulateValue = particulate.value / 24
                C6H6 -> particulateValue = calculateBenzene(particulate.value)
                else -> {
                    // do nothing
                }
            }

            index = Math.max(index, particulateValue)
        }

        return index.toDouble()
    }

    fun calculateBenzene(particulate: Float): Float {
        val value = Math.max(particulate, 0f)
        return when (value) {
            in 0f until 5f -> value * 0.2f// Index 0-1
            in 5f until 20f -> value * 0.4f - 1 // Index 1-7
            else -> value * 0.1f + 5 // Index 7 and above
        }
    }
}

private class FloatRange(override val start: Float, override val endInclusive: Float) : ClosedRange<Float> {
    override fun contains(value: Float): Boolean = value >= start && value < endInclusive
}

private infix fun Float.until(to: Float): FloatRange {
    if (this > to) throw IllegalArgumentException("The to argument value '$to' was too small.")
    return FloatRange(this, to)
}