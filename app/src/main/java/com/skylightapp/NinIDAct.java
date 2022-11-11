package com.skylightapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

public class NinIDAct extends AppCompatActivity {
    private Bundle bundle;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private String userNIN,state,org;
    private String key;
    SharedPreferences.Editor editor;
    Intent intent;

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
        //userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userNIN = userPreferences.getString("PROFILE_NIN", "");
        //setResult(Activity.RESULT_OK, new Intent());
    }

    public class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.nin_pref, rootKey);

            for(int x = 0; x < getPreferenceScreen().getPreferenceCount(); x++){
                PreferenceCategory lol = (PreferenceCategory) getPreferenceScreen().getPreference(x);
                for(int y = 0; y < lol.getPreferenceCount(); y++){
                    Preference pref = lol.getPreference(y);
                    pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            if(preference !=null){
                                key = preference.getKey();


                            }
                            if(key !=null){
                                if ("PROFILE_NIN".equals(key)) {
                                    if ((userNIN = userPreferences.getString("PROFILE_NIN", null)) == null) {
                                        editor.putString("PROFILE_NIN", userNIN);
                                        editor.commit();
                                        setResult(Activity.RESULT_OK, new Intent());
                                    }

                                    bundle.putString("PROFILE_NIN", userNIN);
                                    intent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, intent);
                                    return true;
                                }
                                return false;

                            }


                            return false;
                        }

                    });
                }
            }

        }
    }
}