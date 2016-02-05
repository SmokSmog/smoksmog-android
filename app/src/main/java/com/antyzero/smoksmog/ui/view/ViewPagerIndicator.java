package com.antyzero.smoksmog.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.antyzero.smoksmog.R;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class ViewPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    private List<Long> stationIds = new ArrayList<>();

    private int itemSideSize = 0;
    private int itemSpacing = 0;

    private int desiredWidth = 0;
    private int desiredHeight = 0;

    public ViewPagerIndicator( Context context ) {
        super( context );
        init( context );
    }

    public ViewPagerIndicator( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init( context );
    }

    public ViewPagerIndicator( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        init( context );
    }

    private void init( Context context ) {
        itemSideSize = context.getResources().getDimensionPixelSize( R.dimen.indicator_item_size );
        itemSpacing = context.getResources().getDimensionPixelSize( R.dimen.indicator_item_spacing );
    }

    public void setStationIds( List<Long> stationIds ) {
        this.stationIds = stationIds;

        desiredHeight = 0;
        desiredWidth = 0;

        if ( !stationIds.isEmpty() ) {
            desiredHeight = itemSideSize;
            desiredWidth = itemSideSize;
            for ( int i = 1; i < stationIds.size(); i++ ) {
                desiredWidth += itemSideSize + itemSpacing;
            }
        }

        invalidate();
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        setBackgroundColor( Color.CYAN );
        setMeasuredDimension( desiredWidth, desiredHeight );
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
