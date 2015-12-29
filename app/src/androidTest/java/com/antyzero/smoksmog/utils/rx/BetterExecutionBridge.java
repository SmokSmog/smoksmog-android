package com.antyzero.smoksmog.utils.rx;

/**
 * Created by iwopolanski on 29.12.2015.
 */
public interface BetterExecutionBridge {

    void onStart();

    void onError();

    void onEnd();

}
