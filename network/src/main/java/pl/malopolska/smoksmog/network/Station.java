package pl.malopolska.smoksmog.network;

/**
 * Minimal station information.
 */
interface Station {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    /**
     * Unique identifier for station.
     *
     * @return long value
     */
    long getId();

    /**
     * Name for station
     *
     * @return String value
     */
    String getName();
}
