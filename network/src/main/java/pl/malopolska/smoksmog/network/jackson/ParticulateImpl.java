package pl.malopolska.smoksmog.network.jackson;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import pl.malopolska.smoksmog.network.model.Particulate;

public class ParticulateImpl implements Particulate {

    static final String KEY_SHORT_NAME = "short_name";

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty(KEY_SHORT_NAME)
    private String shortName;

    @JsonProperty
    private String norm;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public float getNorm() {
        return Float.valueOf( norm );
    }

    /**
     * Crappy situation at API level when field 'short_name' i returned with extra char at the end
     */
    @JsonAnySetter
    void anySetter( String key, Object value ) {

        if ( key.startsWith( KEY_SHORT_NAME ) ) {
            shortName = String.valueOf( value );
        }
    }
}
