package com.skylightapp.MapAndLoc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.skylightapp.R;

import java.util.Objects;

public class RespTeamPrefAct extends AppCompatActivity {
    Preference preference;
    private Bundle bundle;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private String cateogory,state,org;
    private String key;
    SharedPreferences.Editor editor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        bundle= new Bundle();
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
        intent= new Intent();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        cateogory = userPreferences.getString("EMERGENCY_REPORT_GROUP", "");
        /*if(cateogory !=null){
            setResult(Activity.RESULT_OK, new Intent());
        }
        super.finishAfterTransition();*/


    }

    public class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            //userPreferences = requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext());

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
                                switch (key) {
                                    case "signature":
                                        if ((org = userPreferences.getString("signature", null)) == null) {
                                            org = "Awajima";
                                            editor.putString("signature", org );
                                            editor.commit();

                                        }

                                        bundle.putString("signature","org");
                                        intent.putExtras(bundle);
                                        setResult(Activity.RESULT_OK, intent);


                                        return true;

                                    case "EMERGENCY_REPORT_GROUP":
                                        if ((cateogory = userPreferences.getString("EMERGENCY_REPORT_GROUP", null)) == null) {
                                            cateogory = "All";
                                            editor.putString("EMERGENCY_REPORT_GROUP", cateogory );
                                            editor.commit();
                                            setResult(Activity.RESULT_OK, new Intent());
                                        }

                                        bundle.putString("EMERGENCY_REPORT_GROUP",cateogory);
                                        intent.putExtras(bundle);
                                        setResult(Activity.RESULT_OK, intent);
                                        return true;

                                    case "EMERGENCY_REPORT_STATE":
                                        if ((state = userPreferences.getString("EMERGENCY_REPORT_STATE", null)) == null) {
                                            state = "All";
                                            editor.putString("EMERGENCY_REPORT_STATE", state );
                                            editor.commit();

                                        }
                                        bundle.putString("EMERGENCY_REPORT_STATE",state);
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