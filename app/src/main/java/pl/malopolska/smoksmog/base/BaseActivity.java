package pl.malopolska.smoksmog.base;

import android.support.v7.app.ActionBarActivity;

import pl.malopolska.smoksmog.injection.ActivityComponent;

public abstract class BaseActivity extends ActionBarActivity {

    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
