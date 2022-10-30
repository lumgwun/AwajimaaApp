package com.skylightapp.Markets;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.skylightapp.R;

import java.util.Objects;
import java.util.Set;

public class BizTypeSelectAct extends AppCompatActivity {

    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.biz_pref, rootKey);
        }

        @Override
        public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {

            final String key = preference.getKey();
            String stringValue = newValue.toString();
            if (key.equals("BIZ_TYPE")) {
                preference.setSummary(stringValue);
                setResult(Activity.RESULT_OK, new Intent());

                return true;
            } else {
                return false;
            }

        }
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            userPreferences = requireContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            userPreferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = userPreferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}