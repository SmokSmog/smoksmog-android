package com.antyzero.smoksmog.ui.screen.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.utils.DimenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class WorkingOnActivity extends AppCompatActivity {

    @Bind( R.id.imageViewDragon )
    ImageView imageViewDragon;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_base );
        ButterKnife.bind( this );

        setupDragon();
    }

    /**
     * Setup Dragon image position
     */
    private void setupDragon() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( (ViewGroup.MarginLayoutParams ) imageViewDragon.getLayoutParams() );
        layoutParams.bottomMargin = DimenUtils.getNavBarHeight( this, R.dimen.dragon_margin_bottom_default );
        imageViewDragon.setLayoutParams( layoutParams );
    }
}
