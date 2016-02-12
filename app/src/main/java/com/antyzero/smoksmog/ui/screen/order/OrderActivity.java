package com.antyzero.smoksmog.ui.screen.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.BaseDragonActivity;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class OrderActivity extends BaseDragonActivity {

    private static final String TAG = OrderActivity.class.getSimpleName();

    @Inject
    SmokSmog smokSmog;
    @Inject
    SettingsHelper settingsHelper;
    @Inject
    Logger logger;

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    private List<Station> stationList = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order );

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );

        recyclerView.setPadding(
                0, DimenUtils.getStatusBarHeight( this, R.dimen.nav_bar_height ), 0, 0 );

        SmokSmogApplication.get( this )
                .getAppComponent()
                .plus( new ActivityModule( this ) )
                .inject( this );

        OrderAdapter adapter = new OrderAdapter( stationList );

        recyclerView.setLayoutManager( new LinearLayoutManager( this, VERTICAL, false ) );
        recyclerView.setAdapter( adapter );

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback( adapter );
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper( callback );
        itemTouchHelper.attachToRecyclerView( recyclerView );

        List<Long> stationIds = settingsHelper.getStationIdList();

        stationIds.add( 13L );
        stationIds.add( 4L );
        stationIds.add( 30L );
        stationIds.add( 32L );
        stationIds.add( 44L );

        smokSmog.getApi().stations()
                .subscribeOn( Schedulers.newThread() )
                .flatMap( Observable::from )
                .filter( station -> stationIds.contains( station.getId() ) )
                .toSortedList( ( station, station2 ) -> stationIds.indexOf( station.getId() ) - stationIds.indexOf( station2.getId() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        stations -> {
                            stationList.clear();
                            stationList.addAll( stations );
                            recyclerView.getAdapter().notifyDataSetChanged();
                        },
                        throwable -> {
                            logger.w( TAG, "Unable to build list", throwable );
                        } );

    }

    public static void start( Context context ) {
        context.startActivity( new Intent( context, OrderActivity.class ) );
    }
}
