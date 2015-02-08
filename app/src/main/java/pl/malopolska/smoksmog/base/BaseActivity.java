package pl.malopolska.smoksmog.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.injection.ActivityComponent;

public class BaseActivity extends ActionBarActivity {

    private ActivityComponent activityComponent;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SmokSmogApplication.get(this).getApplicationComponent().inject(this);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}
