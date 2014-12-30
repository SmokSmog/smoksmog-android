package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StationImpl implements Station {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    @JsonProperty( KEY_ID )
    private long id;

    @JsonProperty( KEY_NAME )
    private String name;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
