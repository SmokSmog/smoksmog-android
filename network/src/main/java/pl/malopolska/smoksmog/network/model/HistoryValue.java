package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.joda.time.DateTime;

import pl.malopolska.smoksmog.network.jackson.HistoryValueImpl;

@JsonDeserialize( as = HistoryValueImpl.class )
public interface HistoryValue {

    float getValue();

    DateTime getDate();
}
