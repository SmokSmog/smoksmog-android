package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.antyzero.smoksmog.SmokSmogApplication;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        activityComponent = SmokSmogApplication.get( this ).getAppComponent()
                .plus( new ActivityModule( this ) );
    }

    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
