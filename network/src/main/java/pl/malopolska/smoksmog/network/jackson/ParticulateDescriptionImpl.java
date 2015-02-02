package pl.malopolska.smoksmog.network.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.malopolska.smoksmog.network.model.ParticulateDescription;

public class ParticulateDescriptionImpl implements ParticulateDescription {

    @JsonProperty
    private String desc;

    @Override
    public String getDescription() {
        return desc;
    }
}
