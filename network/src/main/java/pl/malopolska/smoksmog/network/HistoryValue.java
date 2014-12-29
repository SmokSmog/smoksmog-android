package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 29.12.14.
 */
@JsonDeserialize( as = HistoryValueImpl.class )
public interface HistoryValue {

    float getValue();

    DateTime getDate();
}
