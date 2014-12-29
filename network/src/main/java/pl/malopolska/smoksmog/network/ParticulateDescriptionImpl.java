package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticulateDescriptionImpl implements ParticulateDescription {

    @JsonProperty
    private String desc;

    @Override
    public String getDescription() {
        return desc;
    }
}
