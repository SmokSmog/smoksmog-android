package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by iwopolanski on 26.12.14.
 */
public final class ParticulateImpl implements Particulate {

    private static final DateTimeFormatter DTF =
            DateTimeFormat.forPattern( "YYYY-MM-dd HH:mm:ss" );

    @JsonProperty( KEY_ID )
    private long id;

    @JsonProperty( KEY_NAME )
    private String name;

    @JsonProperty( KEY_SHORT_NAME)
    private String shortName;

    @JsonProperty( KEY_UNIT )
    private String unit;

    @JsonProperty( KEY_VALUE )
    private String value;

    @JsonProperty( KEY_NORM )
    private String norm;

    @JsonProperty( KEY_AVG )
    private String average;

    @JsonProperty( KEY_DATE )
    private String date;

    @JsonProperty( KEY_POSITION )
    private String position;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public float getValue() {
        return Float.valueOf( value );
    }

    @Override
    public float getNorm() {
        return Float.valueOf( norm );
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
