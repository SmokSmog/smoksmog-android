package pl.malopolska.smoksmog.network.jackson;

import java.util.ArrayList;
import java.util.Collection;

import pl.malopolska.smoksmog.network.model.HistoryValue;
import pl.malopolska.smoksmog.network.model.ParticulateHistory;

public class ParticulateHistoryImpl extends ParticulateImpl implements ParticulateHistory {

    private Collection<HistoryValue> values = new ArrayList<>();

    @Override
    public Collection<HistoryValue> getValues() {
        return values;
    }
}
