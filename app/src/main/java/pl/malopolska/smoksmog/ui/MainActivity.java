package pl.malopolska.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.BaseActivity;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.data.Dagger_DataManager;
import pl.malopolska.smoksmog.data.NetworkModule;

public class MainActivity extends BaseActivity {

    @InjectView( R.id.toolbar )
    private Toolbar toolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        setSupportActionBar(toolbar);
    }
}
