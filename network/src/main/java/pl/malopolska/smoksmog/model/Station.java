package pl.malopolska.smoksmog.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Station {

    private long id;
    private String name;
    @SerializedName( "lon" )
    private float longitude;
    @SerializedName( "lat" )
    private float latitude;
    private final List<Particulate> particulates = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Station station = ( Station ) o;

        return id == station.id;

    }

    @Override
    public int hashCode() {
        return ( int ) ( id ^ ( id >>> 32 ) );
    }
}
