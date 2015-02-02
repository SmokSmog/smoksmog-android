package pl.malopolska.smoksmog.network.jackson;

import java.util.ArrayList;
import java.util.Collection;

import pl.malopolska.smoksmog.network.jackson.StationImpl;
import pl.malopolska.smoksmog.network.model.ParticulateDetails;
import pl.malopolska.smoksmog.network.model.StationParticulates;

public class StationParticulatesImpl extends StationImpl implements StationParticulates {

    private final Collection<ParticulateDetails> particulates = new ArrayList<>();

    @Override
    public Collection<ParticulateDetails> getParticulates() {
        return particulates;
    }
}
