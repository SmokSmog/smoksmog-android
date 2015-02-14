package pl.malopolska.smoksmog.ui.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import pl.malopolska.smoksmog.R;

public class SmokSmogPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource( R.xml.preferences );

        Toast.makeText(getActivity(), "added", Toast.LENGTH_SHORT).show();
    }
}
