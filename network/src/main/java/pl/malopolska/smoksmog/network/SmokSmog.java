package pl.malopolska.smoksmog.network;

import android.content.Context;

import dagger.ObjectGraph;

/**
 *
 */
public final class SmokSmog {

    private final ObjectGraph objectGraph;

    /**
     *
     * 
     * @param context
     */
    public SmokSmog( Context context ) {

        objectGraph = ObjectGraph.create( new SmokSmogModule(context) );
        objectGraph.inject( this );
    }
}
