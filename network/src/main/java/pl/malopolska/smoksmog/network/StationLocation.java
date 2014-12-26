package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Station with location details
 */
@JsonDeserialize( as = StationLocationImpl.class )
public interface StationLocation extends Station {

    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGITUDE = "lon";

    /**
     * Station latitude.
     *
     * @return float value
     */
    float getLatitude();

    /**
     * Station longitude.
     *
     * @return float value
     */
    float getLongitude();
}
