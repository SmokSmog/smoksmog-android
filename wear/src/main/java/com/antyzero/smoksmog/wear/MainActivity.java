package com.antyzero.smoksmog.wear;

import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.SmokSmog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import smoksmog.air.AirQualityIndex;
import smoksmog.logger.Logger;


public class MainActivity extends RxActivity implements WatchViewStub.OnLayoutInflatedListener {

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;

    @Bind( R.id.textViewAirQualityIndex )
    TextView textViewAirQualityIndex;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        SmokSmogWearApplication.get( this ).getApplicationComponent().inject( this );
        ( ( WatchViewStub ) findViewById( R.id.watch_view_stub ) ).setOnLayoutInflatedListener( this );
    }

    @Override
    public void onLayoutInflated( WatchViewStub watchViewStub ) {
        ButterKnife.bind( this, watchViewStub );

        smokSmog.getApi().station( 13L )
                .compose( bindUntilEvent( ActivityEvent.DESTROY ) )
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .map( AirQualityIndex::calculate )
                .subscribe( aDouble -> {
                            textViewAirQualityIndex.setText( "" + aDouble );
                        },
                        throwable -> {
                            Toast.makeText( MainActivity.this, "Unable to get station", Toast.LENGTH_SHORT ).show();
                        } );
    }
}
