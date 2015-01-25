package pl.malopolska.smoksmog.network;

/**
 * Minimal station information.
 */
public interface Station {

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
