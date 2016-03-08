package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AddStationDialog extends DialogFragment implements StationDialogAdapter.StationListener {

    private static final String TAG = AddStationDialog.class.getSimpleName();
    public static final String KEY_STATION_IDS = "KEY_STATION_IDS";

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    private final List<Station> stationList = new ArrayList<>();
    private final List<Long> stationIdsNotToShow = new ArrayList<>();
    private StationDialogAdapter.StationListener stationListener;

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        if ( !( activity instanceof StationDialogAdapter.StationListener ) ) {
            throw new IllegalStateException( "Activity needs to implement StationListener" );
        }

        if ( !getArguments().containsKey( KEY_STATION_IDS ) ) {
            throw new IllegalStateException( "Missing station id values" );
        }

        long[] stationIdsArray = getArguments().getLongArray( KEY_STATION_IDS );
        if ( stationIdsArray != null ) {
            for ( long aStationIdsArray : stationIdsArray ) {
                stationIdsNotToShow.add( aStationIdsArray );
            }
        }

        stationListener = ( StationDialogAdapter.StationListener ) activity;

        SmokSmogApplication.get( activity )
                .getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new SupportFragmentModule( AddStationDialog.this ) )
                .inject( this );

    }

    @Override
    public void onStart() {
        super.onStart();
        stationList.clear();
        smokSmog.getApi().stations()
                .subscribeOn( Schedulers.newThread() )
                .flatMap( Observable::from )
                .filter( station -> !stationIdsNotToShow.contains( station.getId() ) )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        stationList::add,
                        throwable -> logger.e( TAG, "Unable to load stations", throwable ),
                        () -> recyclerView.getAdapter().notifyDataSetChanged() );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        View view = getActivity().getLayoutInflater().inflate( R.layout.view_recyclerview, null, false );

        ButterKnife.bind( this, view );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false ) );
        recyclerView.setAdapter( new StationDialogAdapter( stationList, this ) );

        builder.setView( view );
        return builder.create();
    }

    public static void show( FragmentManager supportFragmentManager, long[] stationIdsArray ) {
        DialogFragment dialogFragment = new AddStationDialog();
        Bundle bundle = new Bundle();
        bundle.putLongArray( KEY_STATION_IDS, stationIdsArray );
        dialogFragment.setArguments( bundle );
        dialogFragment.show( supportFragmentManager, TAG );
    }

    @Override
    public void onStation( long stationId ) {
        stationListener.onStation( stationId );
        dismiss();
    }
}
