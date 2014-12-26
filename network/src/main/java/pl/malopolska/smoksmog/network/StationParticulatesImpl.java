package pl.malopolska.smoksmog.network;

import java.util.ArrayList;
import java.util.Collection;

public class StationParticulatesImpl extends StationImpl implements StationParticulates {

    private final Collection<Particulate> particulates = new ArrayList<>();

    @Override
    public Collection<Particulate> getParticulates() {
        return particulates;
    }
}
