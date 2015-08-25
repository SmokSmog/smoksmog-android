package pl.malopolska.smoksmog.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Particulate {

    private long id;
    private String name;
    @SerializedName( "short_name" )
    private String shortName;
    private float value;
    private String unit;
    private float norm;
    private String date;
    @SerializedName( "avg" )
    private float average;
    private int position;

    private List<History> values = new ArrayList<>();
}
