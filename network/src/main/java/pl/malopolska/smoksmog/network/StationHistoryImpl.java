package pl.malopolska.smoksmog.network;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by iwopolanski on 29.12.14.
 */
public class StationHistoryImpl extends StationImpl implements StationHistory {

    private final Collection<ParticulateHistory> particulates = new ArrayList<>();

    @Override
    public Collection<ParticulateHistory> getParticulates() {
        return particulates;
    }
}
