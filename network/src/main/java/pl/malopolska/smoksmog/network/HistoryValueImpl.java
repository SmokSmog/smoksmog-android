package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 29.12.14.
 */
public class HistoryValueImpl implements HistoryValue {

    @JsonProperty
    private float value;

    @JsonProperty
    private DateTime date;

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public DateTime getDate() {
        return date;
    }
}
