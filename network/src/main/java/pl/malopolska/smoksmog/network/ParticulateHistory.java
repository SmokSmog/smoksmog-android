package pl.malopolska.smoksmog.network;

import java.util.Collection;

/**
 * 
 */
public interface ParticulateHistory extends Particulate {

    Collection<ValueHistory> getValues();
}
