package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;
import android.view.Window;

import java.util.Objects;

public class UserPrefActivity extends AppCompatActivity {
    private static final String TITLE_TAG = "UserPrefActivity Title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_pref);
        try {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.settings, new HeaderFragment())
                        .commit();
            } else {
                setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
            }
            getSupportFragmentManager().addOnBackStackChangedListener(
                    () -> {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            setTitle(R.string.title_activity_settings);
                        }
                    });

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(TITLE_TAG, getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

    //@Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                Objects.requireNonNull(pref.getFragment()));
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings, fragment)
                    .addToBackStack(null)
                    .commit();

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }



        setTitle(pref.getTitle());
        return true;
    }

    public static class HeaderFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            try {
                setPreferencesFromResource(R.xml.header_preferences, rootKey);

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            try {
                setPreferencesFromResource(R.xml.user_pref, rootKey);

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }

    public static class CountryFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            try {
                setPreferencesFromResource(R.xml.country_pref, rootKey);

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }
    public static class OtherCountryFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            try {
                setPreferencesFromResource(R.xml.other_country_pref, rootKey);

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }



        }

    }
}