package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collection;

/**
 * Represents brief information about station measurement state.
 */
@JsonDeserialize( as = StationParticulatesImpl.class )
public interface StationParticulates extends Station {

    /**
     * Current state of measurement for station
     *
     * @return collection of particulates
     */
    Collection<ParticulateDetails> getParticulates();
}
