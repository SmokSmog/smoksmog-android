package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collection;

/**
 *
 */
@JsonDeserialize( as = ParticulateHistoryImpl.class )
public interface ParticulateHistory extends Particulate {

    Collection<HistoryValue> getValues();
}
