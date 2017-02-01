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

        var index = 0.0

        for (particulate in particulates) {

            var particulateValue = 0.0

            when (particulate.enum) {
                SO2 -> particulateValue = (particulate.value / 350).toDouble()
                NO2 -> particulateValue = (particulate.value / 200).toDouble()
                CO -> particulateValue = (particulate.value / 10000).toDouble()
                O3 -> particulateValue = (particulate.value / 120).toDouble()
                PM10 -> particulateValue = (particulate.value / 100).toDouble()
                PM25 -> particulateValue = (particulate.value / 60).toDouble()
                C6H6 -> particulateValue = calculateBenzene(particulate)
                else -> {
                    // do nothing
                }
            }

            index = Math.max(index, particulateValue)
        }

        return index * 5
    }

    private fun calculateBenzene(particulate: Particulate): Double {
        val value = particulate.value.toDouble()
        val vp = (1 / (value * 5))
        if (value <= 5) { // Index 0-1
            return (value / 25)
        } else if (value <= 10) { // Index 1-3
            return (1 / (vp * (100 / 3) + (-1 / 3)))
        } else if (value <= 15) { // Index 3-5
            return (1 / (vp * 20 + (-1 / 15)))
        } else if (value <= 20) { // Index 5-7
            return (1 / (vp * (120 / 7) + (1 / 35)))
        } else { // Index 7 and more
            return (1 / (vp * (50 / 7) + (5 / 70))) // 25 - for index 7-10 and more
        }
    }
}
