package com.antyzero.smoksmog.ui.screen.start.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.ui.utils.ForwardingList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StationIdList extends ForwardingList<Long> {

    @Inject
    SettingsHelper settingsHelper;

    public StationIdList( Context context ) {
        SmokSmogApplication.get( context )
                .getAppComponent()
                .inject( this );
    }

    @Override
    public Long get( int location ) {
        if ( settingsHelper.isClosesStationVisible() ) {
            return location == 0 ? 0L : super.get( location - 1 );
        } else {
            return super.get( location );
        }
    }

    @Override
    public int size() {
        return super.size() + ( settingsHelper.isClosesStationVisible() ? 1 : 0 );
    }

    @Override
    protected List<Long> delegate() {
        return settingsHelper.getStationIdList();
    }
}
