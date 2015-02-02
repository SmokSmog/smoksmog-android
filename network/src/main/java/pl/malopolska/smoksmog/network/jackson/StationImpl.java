package pl.malopolska.smoksmog.network.jackson;

import pl.malopolska.smoksmog.network.model.Station;

public abstract class StationImpl implements Station {

    private long id;

    private String name;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
