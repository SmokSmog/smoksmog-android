package smoksmog.air;

import java.util.Collection;

import pl.malopolska.smoksmog.model.Particulate;
import pl.malopolska.smoksmog.model.Station;

/**
 * Created by iwopolanski on 25.01.16.
 */
public class AirQualityIndex {

    public static double calculate(Station station) {
        return calculate(station.getParticulates());
    }

    /**
     * Calculation of air quality index, more details at
     * http://sojp.wios.warszawa.pl/index.php?page=ggg
     *
     * @param particulates collection
     * @return index value
     */
    public static double calculate(Collection<Particulate> particulates) {

        double index = 0;

        for (Particulate particulate : particulates) {

            double particulateValue = 0;

            switch (particulate.getEnum()) {
                case SO2:
                    particulateValue = particulate.getValue() / 350;
                    break;
                case NO2:
                    particulateValue = particulate.getValue() / 200;
                    break;
                case CO:
                    particulateValue = particulate.getValue() / 10_000;
                    break;
                case O3:
                    particulateValue = particulate.getValue() / 120;
                    break;
                case PM10:
                    particulateValue = particulate.getValue() / 100;
                    break;
                case C6H6:
                    particulateValue = particulate.getValue() / 40;
                    break;
                default: // also case UNKNOWN:
                    // do nothing
            }

            index = Math.max(index, particulateValue);
        }

        return index * 5;
    }
}
