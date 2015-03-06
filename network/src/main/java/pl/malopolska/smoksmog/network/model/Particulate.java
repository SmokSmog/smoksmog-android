package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pl.malopolska.smoksmog.network.jackson.ParticulateDetailsImpl;

@JsonDeserialize( as = ParticulateDetailsImpl.class )
public interface Particulate {

    long getId();

    String getName();

    String getShortName();

    float getNorm();
}
