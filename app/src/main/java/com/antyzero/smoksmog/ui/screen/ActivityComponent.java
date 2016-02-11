package com.antyzero.smoksmog.ui.screen;

import com.antyzero.smoksmog.google.GoogleModule;
import com.antyzero.smoksmog.ui.screen.about.AboutActivity;
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity;
import com.antyzero.smoksmog.ui.screen.order.OrderActivity;
import com.antyzero.smoksmog.ui.screen.start.StartActivity;
import com.antyzero.smoksmog.ui.screen.start.StationFragmentComponent;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                ActivityModule.class,
        }
)
public interface ActivityComponent {

    FragmentComponent plus( FragmentModule fragmentModule );

    StationFragmentComponent plus( SupportFragmentModule fragmentModule, GoogleModule googleModule );

    void inject( HistoryActivity activity );

    void inject( AboutActivity activity );

    void inject( StartActivity startActivity );

    void inject( OrderActivity orderActivity );
}
