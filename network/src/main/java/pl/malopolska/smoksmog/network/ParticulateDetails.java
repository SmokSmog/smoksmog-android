package pl.malopolska.smoksmog.network;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 26.12.14.
 */
public interface ParticulateDetails extends Particulate {

    public static final String KEY_UNIT = "unit";
    public static final String KEY_VALUE = "value";
    public static final String KEY_AVG = "avg";
    public static final String KEY_DATE = "date";
    public static final String KEY_POSITION = "position";

    String getUnit();

    float getValue();

    float getAvg();

    DateTime getDate();

    int getPosition();
}
