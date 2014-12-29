package pl.malopolska.smoksmog.network;

import java.util.ArrayList;
import java.util.Collection;

public class ParticulateHistoryImpl extends ParticulateImpl implements ParticulateHistory {

    private Collection<ValueHistory> values = new ArrayList<>();

    @Override
    public Collection<ValueHistory> getValues() {
        return values;
    }
}
