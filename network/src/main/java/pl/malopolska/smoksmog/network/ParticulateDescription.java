package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by iwopolanski on 29.12.14.
 */
@JsonDeserialize( as = ParticulateDescriptionImpl.class )
public interface ParticulateDescription {

    String getDescription();
}
