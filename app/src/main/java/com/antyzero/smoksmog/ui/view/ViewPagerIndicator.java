package com.antyzero.smoksmog.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

    private int itemSpacing = 0;
    private int itemSideSize = 0;
    private int itemSideHalfSize = 0;

    private int desiredWidth = 0;
    private int desiredHeight = 0;

    private Paint paintActiveIndicator;
    private Paint paintIndicator;

    private Path arrow;

    private int activePosition = 0;

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
        itemSpacing = context.getResources().getDimensionPixelSize( R.dimen.indicator_item_spacing );
        itemSideSize = context.getResources().getDimensionPixelSize( R.dimen.indicator_item_size );
        itemSideHalfSize = itemSideSize / 2;

        paintActiveIndicator = new Paint();
        paintActiveIndicator.setColor( getResources().getColor( R.color.iron ) );
        paintActiveIndicator.setStyle( Paint.Style.FILL );
        paintActiveIndicator.setStrokeWidth( 0 );
        paintActiveIndicator.setAntiAlias( true );

        paintIndicator = new Paint( paintActiveIndicator );
        paintIndicator.setAlpha( 127 );

        arrow = new Path();
        arrow.reset();
        arrow.moveTo( itemSideHalfSize, itemSideHalfSize );
        arrow.lineTo( itemSideHalfSize, itemSideSize );
        arrow.lineTo( itemSideSize, 0 );
        arrow.lineTo( 0, itemSideHalfSize );
        arrow.close();
    }

    public void setStationIds( List<Long> stationIds ) {
        this.stationIds = stationIds;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

        desiredHeight = 0;
        desiredWidth = 0;

        if ( !stationIds.isEmpty() ) {
            desiredHeight = itemSideSize;
            desiredWidth = itemSideSize;
            for ( int i = 1; i < stationIds.size(); i++ ) {
                desiredWidth += itemSideSize + itemSpacing;
            }
        }

        setMeasuredDimension( desiredWidth, desiredHeight );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
        for ( int i = 0; i < stationIds.size(); i++ ) {
            Paint paint = paintIndicator;

            if ( i == activePosition ) {
                paint = paintActiveIndicator;
            }

            if ( stationIds.get( i ) <= 0 ) {
                canvas.save();
                canvas.translate( ( itemSideSize + itemSpacing ) * i, 0 );
                canvas.drawPath( arrow, paint );
                canvas.restore();
            } else {
                int cx = itemSideHalfSize + ( itemSideSize + itemSpacing ) * i;
                int cy = this.itemSideHalfSize;
                canvas.drawCircle( cx, cy, this.itemSideHalfSize, paint );
            }
        }
    }

    @Override
    public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

    }

    @Override
    public void onPageSelected( int position ) {
        if ( position != activePosition ) {
            activePosition = position;
            postInvalidate();
            // TODO we may ask to redraw only specific areas ?
        }
    }

    @Override
    public void onPageScrollStateChanged( int state ) {

    }
}
