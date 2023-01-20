package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.util.Objects;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class UserPrefActivity extends AppCompatActivity {
    private static final String TITLE_TAG = "UserPrefActivity Title";
    private static final String PREF_NAME = "awajima";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    private String time,bizName, officeBranch;
    private int recipientId;
    private QBUser cusQBUser;
    private Profile cusProf;
    private MarketBusiness marketBusiness;
    private long bizID;
    private int marKetID, bizIDInt, adminProfID;
    private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private  Bundle messageBundle;
    private int sendeeProfileID;
    private int customerID;
    private int SharedPrefAdminID;
    private DBHelper selector;
    private  Profile sendeeProfile,senderProfile;
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_pref);
        //FirebaseApp.initializeApp(this);
        senderProfile= new Profile();
        adminUser= new AdminUser();
        marketBusiness= new MarketBusiness();
        gson= new Gson();
        gson1= new Gson();
        gson2= new Gson();
        //QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        //QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        setTitle("My Preferences");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastAdminProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);

        json2 = userPreferences.getString("LastAdminProfileUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefAdminID = userPreferences.getInt("ADMIN_ID",0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        try {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.settings, new SettingsFragment())
                        .commit();
            } else {
                setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
            }
            getSupportFragmentManager().addOnBackStackChangedListener(
                    () -> {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            setTitle(R.string.title_activity_settings);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.settings, new SettingsFragment())
                                    .commit();
                        }
                    });


            /*getSupportFragmentManager().addOnBackStackChangedListener(
                    () -> {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.settings, new CountryFragment())
                                    .commit();
                        }
                    });

            getSupportFragmentManager().addOnBackStackChangedListener(
                    () -> {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.settings, new OtherCountryFragment())
                                    .commit();
                        }
                    });*/

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

}