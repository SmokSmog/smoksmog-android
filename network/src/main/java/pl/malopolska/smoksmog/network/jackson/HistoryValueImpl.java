package pl.malopolska.smoksmog.network.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import pl.malopolska.smoksmog.network.model.HistoryValue;

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
