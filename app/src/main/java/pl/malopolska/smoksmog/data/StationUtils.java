package pl.malopolska.smoksmog.data;

import android.location.Location;

import java.util.Collection;

import pl.malopolska.smoksmog.geo.GeoUtils;
import pl.malopolska.smoksmog.network.model.StationLocation;

public class StationUtils {

    private StationUtils() {
        throw new IllegalAccessError("Utils class");
    }

    public static StationLocation findClosestStation(Collection<StationLocation> stations, Location lastLocation) {

        double distance = Double.MAX_VALUE;

        StationLocation closestStation = null;

        for (StationLocation station : stations) {

            double tmp = GeoUtils.distance(
                    station.getLatitude(), station.getLongitude(),
                    lastLocation.getLatitude(), lastLocation.getLongitude());

            if (tmp < distance) {
                distance = tmp;
                closestStation = station;
            }
        }
        return closestStation;
    }
}
