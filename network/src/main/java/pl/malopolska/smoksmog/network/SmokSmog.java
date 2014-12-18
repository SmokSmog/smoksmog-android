package pl.malopolska.smoksmog.network;

import android.app.Application;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 *
 */
public final class SmokSmog {

    private final ObjectGraph objectGraph;

    @Inject
    UrlBuilder urlBuilder;

    /**
     *
     *
     * @param application
     */
    public SmokSmog( Application application, Configuration configuration ) {

        Configuration configurationNew = configuration;

        if ( configurationNew == null ) {
            configurationNew = Configuration.DEFAULT;
        }

        objectGraph = ObjectGraph.create( new SmokSmogModule( application, configurationNew ) );
        objectGraph.inject( this );
    }


}
