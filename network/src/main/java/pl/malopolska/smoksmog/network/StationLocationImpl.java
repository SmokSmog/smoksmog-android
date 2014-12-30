package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationLocationImpl extends StationImpl implements StationLocation {

    @JsonProperty( "lat" )
    private float latitude;

    @JsonProperty( "lon" )
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
