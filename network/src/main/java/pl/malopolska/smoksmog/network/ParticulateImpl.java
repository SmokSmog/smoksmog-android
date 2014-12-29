package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticulateImpl implements Particulate {

    @JsonProperty( KEY_ID )
    private long id;

    @JsonProperty( KEY_NAME )
    private String name;

    @JsonProperty( KEY_SHORT_NAME )
    private String shortName;

    @JsonProperty( KEY_NORM )
    private String norm;

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
    public float getNorm() {
        return Float.valueOf( norm );
    }
}
