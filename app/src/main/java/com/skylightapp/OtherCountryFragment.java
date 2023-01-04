package com.skylightapp;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class OtherCountryFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        try {
            setPreferencesFromResource(R.xml.other_country_pref, rootKey);

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }



    }

}
