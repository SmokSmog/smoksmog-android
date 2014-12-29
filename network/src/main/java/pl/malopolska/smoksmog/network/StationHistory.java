package pl.malopolska.smoksmog.network;

import java.util.Collection;

/**
 * Created by iwopolanski on 29.12.14.
 */
public interface StationHistory extends Station {

    /**
     * Current state of measurement for station
     *
     * @return collection of particulates
     */
    Collection<ParticulateHistory> getParticulates();
}
