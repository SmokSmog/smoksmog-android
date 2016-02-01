package com.antyzero.smoksmog.ui.screen.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by iwopolanski on 01.02.16.
 */
public class StationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        //return super.onCreateView( inflater, container, savedInstanceState );
        View view = new View( getContext() );
        return view;
    }

    public static Fragment newInstance() {
        return new StationFragment();
    }
}
