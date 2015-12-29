package com.antyzero.smoksmog.utils.rx;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.plugins.RxJavaObservableExecutionHook;

/**
 * Created by iwopolanski on 29.12.2015.
 */
public class BetterExecutionHook extends RxJavaObservableExecutionHook {

    BetterExecutionBridge betterExecutionBridge;

    public BetterExecutionHook( BetterExecutionBridge betterExecutionBridge ) {

        this.betterExecutionBridge = betterExecutionBridge;
    }

    @Override
    public <T> Observable.OnSubscribe<T> onCreate( Observable.OnSubscribe<T> f ) {
        return super.onCreate( f );
    }

    @Override
    public <T> Observable.OnSubscribe<T> onSubscribeStart( Observable<? extends T> observableInstance, Observable.OnSubscribe<T> onSubscribe ) {

        onSubscribe.call( new Subscriber<T>() {
            @Override
            public void onCompleted() {

                betterExecutionBridge.onEnd();
            }

            @Override
            public void onError( Throwable e ) {

                betterExecutionBridge.onEnd();
            }

            @Override
            public void onNext( T t ) {

            }
        } );
        betterExecutionBridge.onStart();
        return onSubscribe;

    }

    @Override
    public <T> Subscription onSubscribeReturn( Subscription subscription ) {
        return subscription;
    }

    @Override
    public <T> Throwable onSubscribeError( Throwable e ) {

        return e;
    }

    @Override
    public <T, R> Observable.Operator<? extends R, ? super T> onLift( Observable.Operator<? extends R, ? super T> lift ) {

        return super.onLift( lift );
    }
}
