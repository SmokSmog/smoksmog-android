package pl.malopolska.smoksmog.network.jackson;

import java.util.ArrayList;
import java.util.Collection;

import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.ParticulateHistory;

public class StationHistoryImpl extends StationImpl implements StationHistory {

    private final Collection<ParticulateHistory> particulates = new ArrayList<>();

    @Override
    public Collection<ParticulateHistory> getParticulates() {
        return particulates;
    }
}
