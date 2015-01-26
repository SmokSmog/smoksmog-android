package pl.malopolska.smoksmog.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.injection.ActivityComponent;
import pl.malopolska.smoksmog.injection.Dagger_ActivityComponent;

public class BaseActivity extends ActionBarActivity {

    private ActivityComponent activityComponent;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = Dagger_ActivityComponent.builder()
                .applicationComponent(SmokSmogApplication.get(this).getApplicationComponent())
                .build();

        activityComponent.inject(this);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}
