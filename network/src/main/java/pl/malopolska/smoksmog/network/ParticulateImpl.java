package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 26.12.14.
 */
public class ParticulateImpl implements Particulate {

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
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getShortName() {
        return null;
    }

    @Override
    public String getUnit() {
        return null;
    }

    @Override
    public float getValue() {
        return 0;
    }

    @Override
    public float getNorm() {
        return 0;
    }

    @Override
    public float getAvg() {
        return 0;
    }

    @Override
    public DateTime getDate() {
        return null;
    }

}
