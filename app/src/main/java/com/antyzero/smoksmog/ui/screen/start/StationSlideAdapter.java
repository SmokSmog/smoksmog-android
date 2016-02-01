package com.antyzero.smoksmog.ui.screen.start;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by iwopolanski on 01.02.16.
 */
public class StationSlideAdapter extends FragmentStatePagerAdapter {

    public StationSlideAdapter( FragmentManager fragmentManager ) {
        super( fragmentManager );
    }

    @Override
    public Fragment getItem( int position ) {
        return StationFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
