package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class ParticulateDetailsImpl extends ParticulateImpl implements ParticulateDetails {

    private static final String KEY_UNIT = "unit";
    private static final String KEY_VALUE = "value";
    private static final String KEY_AVG = "avg";
    private static final String KEY_DATE = "date";
    private static final String KEY_POSITION = "position";

    private static final DateTimeFormatter DTF =
            DateTimeFormat.forPattern( "YYYY-MM-dd HH:mm:ss" );

    @JsonProperty( KEY_UNIT )
    private String unit;

    @JsonProperty( KEY_VALUE )
    private String value;

    @JsonProperty( KEY_AVG )
    private String average;

    @JsonProperty( KEY_DATE )
    private String date;

    @JsonProperty( KEY_POSITION )
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
    public float getAvg() {
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
