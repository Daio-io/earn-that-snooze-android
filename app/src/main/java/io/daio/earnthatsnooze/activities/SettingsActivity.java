package io.daio.earnthatsnooze.activities;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import io.daio.earnthatsnooze.R;
import io.daio.earnthatsnooze.fragments.QuizPreferenceFragment;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return QuizPreferenceFragment.class.getName().equals(fragmentName);
    }


}
