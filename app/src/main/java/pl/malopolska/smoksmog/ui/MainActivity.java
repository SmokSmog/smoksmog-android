package pl.malopolska.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.injection.ApplicationScope;
import pl.malopolska.smoksmog.Dagger_ActivityScope;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.toolbar.ToolbarController;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private ToolbarController toolbarController;

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        ApplicationScope applicationScope = SmokSmogApplication.get(this).getApplicationScope();

        Dagger_ActivityScope.builder()
                .applicationScope(applicationScope)
                .build().inject(this);

        toolbarController = new ToolbarController(this, toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }
}
