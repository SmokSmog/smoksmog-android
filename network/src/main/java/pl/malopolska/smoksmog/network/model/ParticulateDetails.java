package pl.malopolska.smoksmog.network.model;

import org.joda.time.DateTime;

public interface ParticulateDetails extends Particulate {

    String getUnit();

    float getValue();

    float getAverage();

    DateTime getDate();

    int getPosition();
}
