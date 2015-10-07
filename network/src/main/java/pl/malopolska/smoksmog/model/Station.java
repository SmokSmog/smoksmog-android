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
}
