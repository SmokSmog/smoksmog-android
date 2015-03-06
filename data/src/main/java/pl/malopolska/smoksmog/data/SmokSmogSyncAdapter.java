package pl.malopolska.smoksmog.data;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 *
 */
public class SmokSmogSyncAdapter extends AbstractThreadedSyncAdapter {

    public SmokSmogSyncAdapter( Context context, boolean autoInitialize, boolean allowParallelSyncs ) {
        super( context, autoInitialize, allowParallelSyncs );
    }

    public SmokSmogSyncAdapter( Context context, boolean autoInitialize ) {
        super( context, autoInitialize );
    }

    @Override
    public void onPerformSync( Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult ) {

    }
}
