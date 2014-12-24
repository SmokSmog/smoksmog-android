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
        public Builder( Application application ){
            this.application = application;
        }

        public SmokSmog build(){
            return new SmokSmog( this );
        }
    }
}
