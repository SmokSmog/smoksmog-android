package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collection;

import pl.malopolska.smoksmog.network.jackson.StationHistoryImpl;

@JsonDeserialize( as = StationHistoryImpl.class )
public interface StationHistory extends Station {

    Collection<ParticulateHistory> getParticulates();
}
