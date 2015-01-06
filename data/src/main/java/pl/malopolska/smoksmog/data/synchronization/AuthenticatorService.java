package pl.malopolska.smoksmog.data.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Authenticator service required by Android.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
