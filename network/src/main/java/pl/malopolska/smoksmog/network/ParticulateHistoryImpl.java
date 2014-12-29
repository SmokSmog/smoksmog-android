package pl.malopolska.smoksmog.network;

import java.util.ArrayList;
import java.util.Collection;

public class ParticulateHistoryImpl extends ParticulateImpl implements ParticulateHistory {

    private Collection<HistoryValue> values = new ArrayList<>();

    @Override
    public Collection<HistoryValue> getValues() {
        return values;
    }
}
