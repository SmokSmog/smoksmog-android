package com.antyzero.smoksmog.ui.screen.start;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class StationSlideAdapter extends FragmentStatePagerAdapter {

    private final List<Long> stationIds;

    public StationSlideAdapter( FragmentManager fragmentManager, List<Long> stationIds ) {
        super( fragmentManager );
        this.stationIds = stationIds;
    }

    @Override
    public Fragment getItem( int position ) {
        return StationFragment.newInstance( stationIds.get( position ) );
    }

    @Override
    public int getCount() {
        return stationIds.size();
    }
}
