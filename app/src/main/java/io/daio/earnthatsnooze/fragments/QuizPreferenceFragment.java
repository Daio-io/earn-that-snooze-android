package io.daio.earnthatsnooze.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import io.daio.earnthatsnooze.R;

public class QuizPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_quiz);
    }

}
