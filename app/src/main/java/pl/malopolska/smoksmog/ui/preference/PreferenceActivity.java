package pl.malopolska.smoksmog.ui.preference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SmokSmogPreferenceFragment())
                .commit();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PreferenceActivity.class));
    }
}
