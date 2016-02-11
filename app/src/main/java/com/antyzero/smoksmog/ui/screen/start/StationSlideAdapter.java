package com.antyzero.smoksmog.ui.screen.start;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Adapter for sliding pages left-right
 */
public class StationSlideAdapter extends FragmentStatePagerAdapter {

    private final List<Long> stationIds;

    /**
     * Standard constructor
     *
     * @param fragmentManager for fragment manipulations
     * @param stationIds      list of IDs for data download
     */
    public StationSlideAdapter( FragmentManager fragmentManager, List<Long> stationIds ) {
        super( fragmentManager );
        this.stationIds = stationIds;
    }

    @Override
    public int getItemPosition( Object object ) {
        if ( object instanceof StationFragment ) {
            StationFragment stationFragment = (StationFragment) object;
            long stationId = stationFragment.getStationId();
            for( int position = 0; position < stationIds.size(); position++){
                if(stationIds.get( position ) == stationId){
                    return position;
                }
            }
        }
        return PagerAdapter.POSITION_NONE;
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
