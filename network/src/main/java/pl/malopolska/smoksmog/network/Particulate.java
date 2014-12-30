package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Current state of measurement, returned with stations inforamtions
 */
@JsonDeserialize( as = ParticulateDetailsImpl.class )
public interface Particulate {

    long getId();

    String getName();

    String getShortName();

    float getNorm();
}
