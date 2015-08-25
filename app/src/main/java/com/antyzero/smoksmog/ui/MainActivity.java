package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.antyzero.smoksmog.R;

import javax.inject.Inject;

import pl.malopolska.smoksmog.Api;
import pl.malopolska.smoksmog.model.Description;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Inject
    Api api;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        getActivityComponent().inject( this );

        api.particulateDescription( 2 )
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnNext( new Action1<Description>() {
                    @Override
                    public void call( Description description ) {
                        Toast.makeText( MainActivity.this, description.getDesc(), Toast.LENGTH_SHORT ).show();
                    }
                } )
                .subscribe();

    }
}
