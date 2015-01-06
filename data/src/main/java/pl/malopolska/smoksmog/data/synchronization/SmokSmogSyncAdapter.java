package pl.malopolska.smoksmog.data.synchronization;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Provides background synchronization for pollution data.
 */
public class SmokSmogSyncAdapter extends AbstractThreadedSyncAdapter {

    /**
     * {@inheritDoc}
     */
    public SmokSmogSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * {@inheritDoc}
     */
    public SmokSmogSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        // TODO perform
    }
}
