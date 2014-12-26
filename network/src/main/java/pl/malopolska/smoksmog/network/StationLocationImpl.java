package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationLocationImpl extends StationImpl implements StationLocation {

    @JsonProperty( KEY_LATITUDE )
    private float latitude;

    @JsonProperty( KEY_LONGITUDE )
    private float longitude;

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }
}
