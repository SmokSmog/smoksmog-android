package pl.malopolska.smoksmog.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.injection.Dagger_ActivityScope;

public class BaseActivity extends ActionBarActivity {

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dagger_ActivityScope.builder()
                .applicationScope(SmokSmogApplication.get(this).getApplicationScope())
                .build();
    }

    protected GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}
