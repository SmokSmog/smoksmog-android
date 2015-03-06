package pl.malopolska.smoksmog.data;

import android.accounts.Account;
import android.accounts.AccountManager;
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
    final Application application;

    /**
     *
     *
     * @param builder
     */
    private SmokSmog( Builder builder ) {

        smokSmogAPI = builder.smogAPI;

        application = builder.application;

        if(!hasAccount()) {
            createAccount();
        }
    }

    /**
     * Check if there is proper account setup
     */
    private boolean hasAccount() {

        AccountManager accountManager = AccountManager.get(application);

        Account[] accounts = accountManager.getAccountsByType(
                application.getString( R.string.account_type ) );

        return accounts.length != 0;
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
    public static final class Builder {

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
