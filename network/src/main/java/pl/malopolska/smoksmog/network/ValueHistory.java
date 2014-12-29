package pl.malopolska.smoksmog.network;

import org.joda.time.DateTime;

/**
 * Created by iwopolanski on 29.12.14.
 */
public interface ValueHistory {

    float getValue();

    DateTime getDate();
}
