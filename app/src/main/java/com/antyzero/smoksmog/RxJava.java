package com.antyzero.smoksmog;

import rx.Subscription;

/**
 * Created by iwopolanski on 28.10.2015.
 */
public class RxJava {

    public static final Subscription EMPTY_SUBSCRIPTION = new Subscription() {

        @Override
        public void unsubscribe() {

        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    };

    private RxJava() {
        throw new IllegalAccessError("Utils class");
    }
}
