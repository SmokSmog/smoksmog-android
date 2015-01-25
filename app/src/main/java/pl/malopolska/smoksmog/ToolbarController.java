package pl.malopolska.smoksmog;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import pl.malopolska.smoksmog.ui.ActionBarActivity;

public class ToolbarController {

    private final Toolbar toolbar;
    private final ActionBar actionBar;

    public ToolbarController(@NonNull ActionBarActivity activity, @NonNull Toolbar toolbar) {

        this.toolbar = toolbar;

        activity.setSupportActionBar(toolbar);

        actionBar = activity.getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
    }
}
