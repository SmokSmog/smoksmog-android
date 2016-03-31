package com.antyzero.smoksmog.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.android.plugins.RxAndroidPlugins;

/**
 * Created by iwopolanski on 31.03.16.
 */
public class RxSchedulerTestRule implements TestRule {

    @Override
    public Statement apply( Statement base, Description description ) {
        return null;
    }
}
