package com.antyzero.smoksmog.ui.screen.about;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Bind( R.id.textView )
    TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        ButterKnife.bind( this );
        textView.setText( Html.fromHtml( getString( R.string.about ) ) );
        textView.setMovementMethod( LinkMovementMethod.getInstance() );
    }

    public static void start( Context context ) {
        context.startActivity( intent( context ) );
    }

    public static Intent intent( Context context ) {
        return new Intent( context, AboutActivity.class );
    }
}
