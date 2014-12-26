package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StationImpl implements Station {

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
