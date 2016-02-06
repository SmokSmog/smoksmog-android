package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.utils.DimenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base Activity that contains Dragon image and pollution background
 */
public abstract class BaseDragonActivity extends AppCompatActivity {

    private ImageView imageViewDragon;
    private ViewGroup container;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        super.setContentView( R.layout.activity_base );

        imageViewDragon = ( ImageView ) findViewById( R.id.imageViewDragon );
        container = ( ViewGroup ) findViewById( R.id.container );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            int color;
            int colorResourceId = android.R.color.transparent;
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                color = getResources().getColor( colorResourceId, getTheme() );
            } else {
                //noinspection deprecation
                color = getResources().getColor( colorResourceId );
            }
            getWindow().setNavigationBarColor( color );
        }

        setupDragon();
    }

    @Override
    protected void attachBaseContext( Context newBase ) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }

    @Override
    public void setContentView( @LayoutRes int layoutResID ) {
        LayoutInflater.from( this ).inflate( layoutResID, container, true );
        ButterKnife.bind( this );
    }

    @Override
    public void setContentView( View view ) {
        container.addView( view );
        ButterKnife.bind( this );
    }

    @Override
    public void setContentView( View view, ViewGroup.LayoutParams params ) {
        container.addView( view, params );
        ButterKnife.bind( this );
    }

    /**
     * Setup Dragon image position
     */
    private void setupDragon() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( ( ViewGroup.MarginLayoutParams ) imageViewDragon.getLayoutParams() );
        layoutParams.bottomMargin = DimenUtils.getNavBarHeight( this, R.dimen.dragon_margin_bottom_default );
        layoutParams.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM );
        layoutParams.addRule( RelativeLayout.ALIGN_PARENT_RIGHT );
        imageViewDragon.setLayoutParams( layoutParams );
        imageViewDragon.setVisibility( View.VISIBLE );
    }
}
