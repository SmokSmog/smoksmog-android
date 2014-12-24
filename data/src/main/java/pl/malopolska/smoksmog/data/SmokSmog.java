package pl.malopolska.smoksmog.data;

import android.app.Application;

import javax.inject.Inject;

import dagger.ObjectGraph;
import pl.malopolska.smoksmog.network.SmokSmogAPI;

/**
 *
 */
public final class SmokSmog {

    @Inject
    SmokSmogAPI smokSmogAPI;

    /**
     *
     *
     * @param builder
     */
    private SmokSmog( Builder builder ) {

        ObjectGraph objectGraph = ObjectGraph.create( new SmokSmogModule( builder ) );
        objectGraph.inject( this );
    }

    /**
     * Construct SmokSmog object
     */
    static final class Builder {

        final Application application;

        String endpoint = "http://api.smoksmog.jkostrz.name/";

        /**
         *
         *
         * @param application
         */
        public Builder( Application application ){
            this.application = application;
        }

        public Builder setEndpoint( String endpoint ){

            // TODO validate argument

            this.endpoint = endpoint;
            return this;
        }

        public SmokSmog build(){
            return new SmokSmog( this );
        }
    }
}
