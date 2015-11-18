package pl.malopolska.smoksmog.utils;


import java.util.Collection;

import pl.malopolska.smoksmog.model.Particulate;

public class ParticulateUtils {

    private ParticulateUtils() {
        throw new IllegalAccessError( "Utils class" );
    }

    public static Particulate findBy(Collection<Particulate> particulates){

        for( Particulate particulate : particulates ){
            particulate.getId();
        }

        return null;
    }
}
