package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collection;

import pl.malopolska.smoksmog.network.jackson.ParticulateHistoryImpl;

/**
 *
 */
@JsonDeserialize( as = ParticulateHistoryImpl.class )
public interface ParticulateHistory extends Particulate {

    Collection<HistoryValue> getValues();
}
