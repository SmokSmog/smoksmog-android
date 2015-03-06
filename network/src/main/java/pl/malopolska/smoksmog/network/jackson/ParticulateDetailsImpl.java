package pl.malopolska.smoksmog.network.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.malopolska.smoksmog.network.model.ParticulateDetails;

public final class ParticulateDetailsImpl extends ParticulateImpl implements ParticulateDetails {

    private static final DateTimeFormatter DTF =
            DateTimeFormat.forPattern( "YYYY-MM-dd HH:mm:ss" );

    @JsonProperty
    private String unit;

    @JsonProperty
    private String value;

    @JsonProperty( "avg" )
    private String average;

    @JsonProperty
    private String date;

    @JsonProperty
    private String position;

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public float getValue() {
        return Float.valueOf( value );
    }

    @Override
    public float getAverage() {
        return Float.valueOf( average );
    }

    @Override
    public DateTime getDate() {
        return DTF.parseDateTime( date );
    }

    @Override
    public int getPosition() {
        return Integer.valueOf( position );
    }
}
