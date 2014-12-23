package pl.malopolska.smoksmog.network;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 *
 */
public final class SmokSmog {

    @Inject
    UrlBuilder urlBuilder;

    /**
     *
     *
     * @param builder
     */
    private SmokSmog( Builder builder ) {

        ObjectGraph objectGraph = ObjectGraph.create( new SmokSmogModule( builder.application ) );
        objectGraph.inject( this );
    }

    /**
     * Construct SmokSmog object
     */
    public static final class Builder {

        private final Application application;

        /**
         *
         *
         * @param application
         */
        public Builder( @NonNull Application application ){
            this.application = application;
        }

        public SmokSmog build(){
            return new SmokSmog( this );
        }
    }
}
