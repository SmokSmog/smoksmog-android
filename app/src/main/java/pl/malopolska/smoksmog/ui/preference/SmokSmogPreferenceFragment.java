package pl.malopolska.smoksmog.ui.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pl.malopolska.smoksmog.R;

public class SmokSmogPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
