package io.daio.earnthatsnooze;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

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


    // Not required but leaving encase activity is exported in the future
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return AlarmPreferencesFragment.class.getName().equals(fragmentName)
                || QuizPreferenceFragment.class.getName().equals(fragmentName);
    }


}
