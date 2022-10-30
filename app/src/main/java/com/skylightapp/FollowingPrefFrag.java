package com.skylightapp;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class FollowingPrefFrag extends PreferenceFragmentCompat {
    private final static String LOG_TAG = FollowingPrefFrag.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}