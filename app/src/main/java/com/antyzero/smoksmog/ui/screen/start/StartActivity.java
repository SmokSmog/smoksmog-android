package com.antyzero.smoksmog.ui.screen.start;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.error.ErrorReporter;
import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.BaseDragonActivity;
import com.antyzero.smoksmog.ui.dialog.InfoDialog;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.order.OrderActivity;
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity;
import com.antyzero.smoksmog.ui.screen.start.model.StationIdList;
import com.antyzero.smoksmog.ui.view.ViewPagerIndicator;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Base activity for future
 */
public class StartActivity extends BaseDragonActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = StartActivity.class.getSimpleName();

    private static final int PAGE_LIMIT = 5;

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;
    @Inject
    ErrorReporter errorReporter;
    @Inject
    SettingsHelper settingsHelper;
    @Inject
    RxBus rxBus;

    @Bind( R.id.toolbar )
    Toolbar toolbar;
    @Bind( R.id.viewPager )
    ViewPager viewPager;
    @Bind( R.id.viewPagerIndicator )
    ViewPagerIndicator viewPagerIndicator;

    private List<Long> stationIds;
    private StationSlideAdapter stationSlideAdapter;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        SmokSmogApplication.get( this ).getAppComponent()
                .plus( new ActivityModule( this ) )
                .inject( this );

        stationIds = new StationIdList( this );

        setContentView( R.layout.activity_start );
        setSupportActionBar( toolbar );

        stationSlideAdapter = new StationSlideAdapter( getFragmentManager(), stationIds );

        viewPager.setAdapter( stationSlideAdapter );
        viewPager.addOnPageChangeListener( this );
        viewPager.setOffscreenPageLimit( PAGE_LIMIT );
        // TODO this may change
        viewPager.setCurrentItem( 0 );
        viewPager.addOnPageChangeListener( viewPagerIndicator );

        viewPagerIndicator.setStationIds( stationIds );

        correctTitleMargin();

        // Listen for info dialog calls
        rxBus.toObserverable()
                .compose( bindUntilEvent( ActivityEvent.DESTROY ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( o -> {
                    if ( o instanceof InfoDialog.Event ) {
                        InfoDialog.show( getFragmentManager(), ( InfoDialog.Event ) o );
                    }
                } );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            // getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerIndicator.setStationIds( stationIds );
        stationSlideAdapter.notifyDataSetChanged();
    }

    /**
     * Because it's not aligned with main layout margin
     */
    private void correctTitleMargin() {
        toolbar.setContentInsetsAbsolute( 18, 0 );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch ( item.getItemId() ) {
            case R.id.action_settings:
                SettingsActivity.start( this );
                break;
            case R.id.action_order:
                OrderActivity.start( this );
                break;
            case R.id.action_about:
                rxBus.send( new InfoDialog.Event( R.layout.info_about ) );
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onPageScrollStateChanged( int state ) {
        // do nothing
    }

    @Override
    public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {
        // do nothing
    }

    @Override
    public void onPageSelected( int position ) {

        Fragment fragment = stationSlideAdapter.getItem( position );
        if ( fragment instanceof StationFragment ) {
            StationFragment stationFragment = ( StationFragment ) fragment;
            String title = stationFragment.getTitle();
            if ( title != null ) {
                toolbar.setTitle( title );
                toolbar.setSubtitle( stationFragment.getSubtitle() );
            }
        }
    }
}
