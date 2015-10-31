package io.daio.earnthatsnooze;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import io.daio.earnthatsnooze.fragments.AlarmPreferencesFragment;
import io.daio.earnthatsnooze.fragments.QuizPreferenceFragment;

public class AlarmSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }


    // Not required but leaving here for now - activity may be exported in the future
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return AlarmPreferencesFragment.class.getName().equals(fragmentName)
                || QuizPreferenceFragment.class.getName().equals(fragmentName);
    }


}
