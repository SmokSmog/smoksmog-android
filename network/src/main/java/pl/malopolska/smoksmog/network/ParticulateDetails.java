package pl.malopolska.smoksmog.network;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 26.12.14.
 */
public interface ParticulateDetails extends Particulate {

    String getUnit();

    float getValue();

    float getAvg();

    DateTime getDate();

    int getPosition();
}
