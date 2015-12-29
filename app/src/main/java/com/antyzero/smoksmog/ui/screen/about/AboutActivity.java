package com.antyzero.smoksmog.ui.screen.about;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    public static void start( Context context ) {
        context.startActivity( intent( context ) );
    }

    public static Intent intent( Context context ) {
        return new Intent( context, AboutActivity.class );
    }
}
