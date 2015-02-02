package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pl.malopolska.smoksmog.network.jackson.StationLocationImpl;

@JsonDeserialize( as = StationLocationImpl.class )
public interface StationLocation extends Station {

    float getLatitude();

    float getLongitude();
}
