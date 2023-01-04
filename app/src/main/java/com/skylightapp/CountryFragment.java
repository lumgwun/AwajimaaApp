package com.skylightapp;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public  class CountryFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        try {
            setPreferencesFromResource(R.xml.country_pref, rootKey);

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }
}
