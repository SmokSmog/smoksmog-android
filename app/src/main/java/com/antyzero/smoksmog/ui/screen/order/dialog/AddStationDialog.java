package com.antyzero.smoksmog.ui.screen.order.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class AddStationDialog extends DialogFragment {

    private static final String TAG = AddStationDialog.class.getSimpleName();

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    private final List<Station> stationList = new ArrayList<>();

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        SmokSmogApplication.get( activity )
                .getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new SupportFragmentModule( AddStationDialog.this ) )
                .inject( this );
    }

    @Override
    public void onStart() {
        super.onStart();
        smokSmog.getApi().stations()
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        stations -> {
                            stationList.clear();
                            stationList.addAll( stations );
                            recyclerView.getAdapter().notifyDataSetChanged();
                        },
                        throwable -> {
                            logger.e( TAG, "Unable to load stations", throwable );
                        } );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        View view = getActivity().getLayoutInflater().inflate( R.layout.view_recyclerview, null, false );

        ButterKnife.bind( this, view );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false ) );
        recyclerView.setAdapter( new StationDialogAdapter( stationList ) );

        builder.setView( view );
        return builder.create();
    }

    public static void show( FragmentManager supportFragmentManager ) {
        DialogFragment dialogFragment = new AddStationDialog();
        dialogFragment.show( supportFragmentManager, TAG );
    }
}
