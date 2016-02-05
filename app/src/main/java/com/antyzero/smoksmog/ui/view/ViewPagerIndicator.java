package com.antyzero.smoksmog.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *
 */
public class ViewPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    private List<Long> stationIds = new ArrayList<>();

    public ViewPagerIndicator( Context context ) {
        super( context );
    }

    public ViewPagerIndicator( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public ViewPagerIndicator( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public void setStationIds( List<Long> stationIds ){
        this.stationIds = stationIds;
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );


    }

    @Override
    public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

    }

    @Override
    public void onPageSelected( int position ) {

    }

    @Override
    public void onPageScrollStateChanged( int state ) {

    }
}
