package com.antyzero.smoksmog.wear;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends Activity implements WatchViewStub.OnLayoutInflatedListener {

    @Bind( R.id.text )
    TextView mTextView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ( ( WatchViewStub ) findViewById( R.id.watch_view_stub ) ).setOnLayoutInflatedListener( this );
    }

    @Override
    public void onLayoutInflated( WatchViewStub watchViewStub ) {
        ButterKnife.bind( this, watchViewStub );
        mTextView.setText( "asdasd" );
    }
}
