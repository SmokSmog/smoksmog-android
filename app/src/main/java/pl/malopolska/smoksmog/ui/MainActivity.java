package pl.malopolska.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.BaseActivity;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.location.Dagger_GoogleApiComponent;
import pl.malopolska.smoksmog.location.GoogleApiClientModule;
import pl.malopolska.smoksmog.location.GoogleApiComponent;
import pl.malopolska.smoksmog.toolbar.ToolbarController;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private ToolbarController toolbarController;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbarController = new ToolbarController(this, toolbar);

        GoogleApiComponent component = Dagger_GoogleApiComponent.builder()
                .googleApiClientModule(new GoogleApiClientModule(this))
                .build();

    }
}
