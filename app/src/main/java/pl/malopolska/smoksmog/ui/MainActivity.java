package pl.malopolska.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.injection.ApplicationScope;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.SmokSmogApplication;
import pl.malopolska.smoksmog.injection.Dagger_ActivityScope;
import pl.malopolska.smoksmog.toolbar.ToolbarController;

public class MainActivity extends BaseActivity {

    private ToolbarController toolbarController;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbarController = new ToolbarController(this, toolbar);

        Toast.makeText(this, ">> " + getGoogleApiClient() + " <<", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
