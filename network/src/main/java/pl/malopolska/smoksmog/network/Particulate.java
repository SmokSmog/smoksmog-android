package pl.malopolska.smoksmog.network;

import org.joda.time.DateTime;

/**
 * Current state of measurement, returned with stations inforamtions
 */
public interface Particulate {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SHORT_NAME = "short_name";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_VALUE = "value";
    public static final String KEY_NORM = "norm";
    public static final String KEY_AVG = "avg";
    public static final String KEY_DATE = "date";

    long getId();

    String getName();

    String getShortName();

    String getUnit();

    float getValue();

    float getNorm();

    float getAvg();

    DateTime getDate();
}
