package pl.malopolska.smoksmog.utils;


import java.util.Collection;
import java.util.List;

import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.functions.Func1;

public class StationUtils {

    private StationUtils() {
        throw new IllegalAccessError( "Utils class" );
    }

    /**
     * Converts station list to list of station ids
     *
     * @param stationList
     * @return
     */
    public static List<Long> convertStationsToIdsList( List<Station> stationList ) {
        return Observable.from( stationList )
                .map( new Func1<Station, Long>() {
                    @Override
                    public Long call( Station station ) {
                        return station.getId();
                    }
                } )
                .toList().toBlocking().first();
    }

    /**
     *
     *
     * @param stations
     * @return
     */
    public static long[] convertStationsToIdsArray( Collection<Station> stations ){
        long[] result = new long[stations.size()];
        int i = 0;
        for( Station station : stations ){
            result[i] = station.getId();
            i++;
        }
        return result;
    }

    /**
     * @param stations
     * @param longitude
     * @param latitude
     * @return
     */
    public static Station findClosest( Collection<Station> stations, double latitude, double longitude ) {

        Station closestStation = null;
        double distance = Double.MAX_VALUE;

        for ( Station station : stations ) {

            double calculatedDistance = distanceInRadians(
                    station.getLatitude(), station.getLongitude(),
                    latitude, longitude );

            if ( calculatedDistance < distance ) {
                closestStation = station;
                distance = calculatedDistance;
            }
        }

        return closestStation;
    }

    /**
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return
     */
    private static double distanceInRadians( double latitude1, double longitude1, double latitude2, double longitude2 ) {

        double x1 = Math.toRadians( latitude1 );
        double y1 = Math.toRadians( longitude1 );
        double x2 = Math.toRadians( latitude2 );
        double y2 = Math.toRadians( longitude2 );

        // great circle distance in radians
        return Math.acos( Math.sin( x1 ) * Math.sin( x2 ) + Math.cos( x1 ) * Math.cos( x2 ) * Math.cos( y1 - y2 ) );
    }
}
