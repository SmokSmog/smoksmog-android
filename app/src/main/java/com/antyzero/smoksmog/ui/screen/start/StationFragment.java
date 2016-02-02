package com.antyzero.smoksmog.ui.screen.start;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.screen.ActivityModule;
import com.antyzero.smoksmog.ui.screen.FragmentComponent;
import com.antyzero.smoksmog.ui.screen.FragmentModule;
import com.antyzero.smoksmog.ui.screen.SupportFragmentModule;

import butterknife.Bind;

public class StationFragment extends BaseFragment {

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        SmokSmogApplication.get( activity ).getAppComponent()
                .plus( new ActivityModule( activity ) )
                .plus( new SupportFragmentModule( this ) )
                .inject( this );
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_station, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

    }

    public static Fragment newInstance() {
        return new StationFragment();
    }
}
