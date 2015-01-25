package pl.malopolska.smoksmog.data;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import pl.malopolska.smoksmog.network.SmokSmogAPI;

/**
 *
 */
public final class SmokSmog {

    final SmokSmogAPI smokSmogAPI;

    /**
     *
     *
     * @param builder
     */
    private SmokSmog( Builder builder ) {

        smokSmogAPI = builder.smogAPI;

        createAccount();
    }

    /**
     * Access to network API.
     *
     * @return SmokSmogAPI implementation
     */
    public SmokSmogAPI getAPI(){
        return smokSmogAPI;
    }

    /**
     * Creates dumb account for application, only one required.
     */
    private void createAccount() {


    }

    /**
     * Construct SmokSmog object
     */
    static final class Builder {

        final Application application;
        final SmokSmogAPI smogAPI;

        String endpoint = "http://api.smoksmog.jkostrz.name/";

        /**
         *
         *
         * @param application
         */
        public Builder( @NonNull Application application, @NonNull SmokSmogAPI smogAPI ){

            this.application = application;
            this.smogAPI = smogAPI;
        }

        /**
         * Set custom endpoint
         *
         * @param endpoint new value
         * @return Builder object for chain call
         */
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
