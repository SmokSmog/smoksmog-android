package pl.malopolska.smoksmog.network;

import java.util.Collection;

/**
 * Represents brief information about station measurement state.
 */
public interface StationState {

    /**
     * Current state of measurement for station
     *
     * @return collection of particulates
     */
    Collection<Particulate> getParticulates();
}
