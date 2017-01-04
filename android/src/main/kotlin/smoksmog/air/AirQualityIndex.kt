package smoksmog.air

import pl.malopolska.smoksmog.model.Particulate
import pl.malopolska.smoksmog.model.ParticulateEnum.*
import pl.malopolska.smoksmog.model.Station

object AirQualityIndex {

    fun calculate(station: Station): Double {
        return calculate(station.particulates)
    }

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
                C6H6 -> particulateValue = (particulate.value / 40).toDouble()
                else -> {
                    // do nothing
                }
            }

            index = Math.max(index, particulateValue)
        }

        return index * 5
    }
}
