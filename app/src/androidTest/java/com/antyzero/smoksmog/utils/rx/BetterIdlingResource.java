package com.antyzero.smoksmog.utils.rx;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import rx.plugins.RxJavaPlugins;

/**
 * Created by iwopolanski on 29.12.2015.
 */
public class BetterIdlingResource implements IdlingResource, BetterExecutionBridge {

    private static final boolean isLogged = true;
    public static final String TAG = "BetterIdlingResource";
    private IdlingResource.ResourceCallback cb;
    private final AtomicInteger idler = new AtomicInteger( 0 );

    public BetterIdlingResource() {

        try {
            RxJavaPlugins.getInstance().registerObservableExecutionHook( new BetterExecutionHook( this ) );
        } catch ( Exception e ) {
            Log.e( TAG, "Error on Rx plugin register", e );
        }
    }

    @Override
    public String getName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {

        synchronized ( idler ) {
            if ( isLogged ) {
                Log.e( TAG, "called isIdleNow: " + idler, null );
            }
            return idler.get() <= 0;
        }
    }

    @Override
    public void registerIdleTransitionCallback( ResourceCallback resourceCallback ) {

        if ( isLogged ) {
            Log.e( TAG, "called register Idle: " + idler, null );
        }
        this.cb = resourceCallback;
    }

    @Override
    public void onStart() {

        synchronized ( idler ) {
            idler.incrementAndGet();
            if ( isLogged ) {
                Log.e( TAG, "called onstart: " + idler, null );
            }
        }
    }

    @Override
    public void onError() {

        synchronized ( idler ) {
            idler.decrementAndGet();
            if ( isLogged ) {
                Log.e( TAG, "called onerrror: " + idler, null );
            }
            if ( idler.get() <= 0 && cb != null ) {
                cb.onTransitionToIdle();
            }
        }
    }

    @Override
    public void onEnd() {

        synchronized ( idler ) {
            idler.decrementAndGet();
            if ( isLogged ) {
                Log.e( TAG, "called onend: " + idler, null );
            }
            if ( idler.get() <= 0 && cb != null ) {
                cb.onTransitionToIdle();
            }
        }
    }
}
