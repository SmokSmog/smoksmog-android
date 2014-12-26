package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Current state of measurement, returned with stations inforamtions
 */
@JsonDeserialize( as = ParticulateDetailsImpl.class )
public interface Particulate {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SHORT_NAME = "short_name";
    public static final String KEY_NORM = "norm";

    long getId();

    String getName();

    String getShortName();

    float getNorm();
}
