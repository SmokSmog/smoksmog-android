package com.antyzero.smoksmog.ui.screen.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.antyzero.smoksmog.R;

import butterknife.Bind;

/**
 * Base activity for future
 */
public class StartActivity extends BaseDragonActivity {

    @Bind( R.id.viewPager )
    ViewPager viewPager;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );

        viewPager.setAdapter( new StationSlideAdapter( getSupportFragmentManager() ) );
    }
}
