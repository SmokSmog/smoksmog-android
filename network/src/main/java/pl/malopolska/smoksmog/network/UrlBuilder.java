package pl.malopolska.smoksmog.network;

/**
 * Creates URL for SmokSmog API.
 *
 * Remember to provide base URL at constructor also API defines locale in each of URLs, this
 * parameter should be passed at constructor. See method comments below.
 */
public interface UrlBuilder {

    /**
     * Access to base URL used to create rest of them.
     *
     * @return String URL
     */
    String baseUrl();

    /**
     * URL for accessing all available stations
     *
     * Path: /{locale}/stations
     *
     * @return String URL
     */
    String stations();

    /**
     * URL for detailed station information
     *
     * Path: /{locale}/stations/{id}
     *
     * @param stationId identifies station
     * @return String URL
     */
    String station(long stationId);

    /**
     * URL for detailed station information
     *
     * Path: /{locale}/stations/{lat}/{lon}
     *
     * @param latitude
     * @param longitude
     * @return String URL
     */
    String station(float latitude, float longitude);

    /**
     * URL for detailed station history, results from last 30 days.
     *
     * Path: /{locale}/stations/{id}/history
     *
     * @param stationId identifies station
     * @return String URL
     */
    String stationHistory(long stationId);

    /**
     * URL for detailed station history, results from last 30 days.
     *
     * Path: /{locale}/stations/{lat}/{lon}/history
     *
     * @param latitude
     * @param longitude
     * @return String URL
     */
    String stationHistory(float latitude, float longitude);

    /**
     * URL for atmospheric particle description
     *
     * Path: /{locale}/particulates/{id}/desc
     *
     * @param particleId identifies particle
     * @return String URL
     */
    String particulates(long particleId);
}
