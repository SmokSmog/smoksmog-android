package com.antyzero.smoksmog.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.antyzero.smoksmog.R;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity implements ActivityLifecycleProvider {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.lifecycleSubject.onNext( ActivityEvent.CREATE );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {

            int color;

            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                color = getResources().getColor( R.color.primary, getTheme() );
            } else {
                //noinspection deprecation
                color = getResources().getColor( R.color.primary );
            }

            getWindow().setNavigationBarColor( color );
        }
    }

    @Override
    public void setContentView( int layoutResID ) {
        super.setContentView( layoutResID );
        ButterKnife.bind( this );
    }

    @Override
    public void setContentView( View view ) {
        super.setContentView( view );
        ButterKnife.bind( this );
    }

    @Override
    public void setContentView( View view, ViewGroup.LayoutParams params ) {
        super.setContentView( view, params );
        ButterKnife.bind( this );
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext( ActivityEvent.START );
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext( ActivityEvent.RESUME );
    }

    @Override
    protected void onPause() {
        this.lifecycleSubject.onNext( ActivityEvent.PAUSE );
        super.onPause();
    }

    @Override
    protected void onStop() {
        this.lifecycleSubject.onNext( ActivityEvent.STOP );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.lifecycleSubject.onNext( ActivityEvent.DESTROY );
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext( Context newBase ) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }

    @Override
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.asObservable();
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindUntilEvent( ActivityEvent event ) {
        return RxLifecycle.bindUntilActivityEvent( this.lifecycleSubject, event );
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity( this.lifecycleSubject );
    }

    public BehaviorSubject<ActivityEvent> getLifecycleSubject() {
        return lifecycleSubject;
    }
}
