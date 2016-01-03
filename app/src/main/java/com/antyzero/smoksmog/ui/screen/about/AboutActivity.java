package com.antyzero.smoksmog.ui.screen.about;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.ActivityModule;
import com.antyzero.smoksmog.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";
    
    @Inject
    Logger logger;

    @Bind( R.id.textView )
    TextView textView;
    @Bind( R.id.textViewVersionName )
    TextView textViewVersionName;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        SmokSmogApplication.get( this ).getAppComponent().plus( new ActivityModule( this ) ).inject( this );
        setContentView( R.layout.activity_about );
        ButterKnife.bind( this );

        textView.setText( Html.fromHtml( getString( R.string.about ) ) );
        textView.setMovementMethod( LinkMovementMethod.getInstance() );

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo( getPackageName(), 0 );

            textViewVersionName.setText( getString( R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode ) );

        } catch ( Exception e ) {
            logger.e( TAG, "Problem with obaining version", e );
        }
    }

    public static void start( Context context ) {
        context.startActivity( intent( context ) );
    }

    public static Intent intent( Context context ) {
        return new Intent( context, AboutActivity.class );
    }
}
