package pl.malopolska.smoksmog.network.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.malopolska.smoksmog.network.model.StationLocation;

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
