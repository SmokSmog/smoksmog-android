package pl.malopolska.smoksmog.network;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticulateImpl implements Particulate {

    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_SHORT_NAME = "short_name";
    static final String KEY_NORM = "norm";

    @JsonProperty( KEY_ID )
    private long id;

    @JsonProperty( KEY_NAME )
    private String name;

    @JsonProperty( KEY_SHORT_NAME )
    private String shortName;

    @JsonProperty( KEY_NORM )
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
    void anySetter( String key, Object value ){

        if(key != null && key.startsWith( KEY_SHORT_NAME )){
            shortName = String.valueOf( value );
        }
    }
}
