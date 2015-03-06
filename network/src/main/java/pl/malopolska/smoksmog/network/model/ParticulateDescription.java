package pl.malopolska.smoksmog.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pl.malopolska.smoksmog.network.jackson.ParticulateDescriptionImpl;

@JsonDeserialize( as = ParticulateDescriptionImpl.class )
public interface ParticulateDescription {

    String getDescription();
}
