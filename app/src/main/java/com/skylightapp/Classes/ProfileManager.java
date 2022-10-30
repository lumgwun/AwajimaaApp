package com.skylightapp.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.gson.Gson;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Enums.ProfileStatus;
import com.skylightapp.Interfaces.OnObjectChangedListener;
import com.skylightapp.Interfaces.OnObjectExistListener;
import com.skylightapp.Interfaces.OnProfileCreatedListener;
import com.skylightapp.Interfaces.UploadImagePrefix;
import com.skylightapp.SignTabMainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileManager extends FirebaseListenersManager {
    public static final String CHANNEL_ID = "ProfileManager.CHANNEL_ID";


    private static final String TAG = ProfileManager.class.getSimpleName();
    private static ProfileManager instance;
    SharedPreferences sharedPreferences;

    DBHelper dbHelper;
    Profile userProfile;
    private ProfDAO profileDao;
    Gson gson;
    String json,user;

    private Context context;
    private DatabaseHelper databaseHelper;
    //private Map<Context, List<ValueEventListener>> activeListeners = new HashMap<>();


    public static ProfileManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProfileManager(context);
        }

        return instance;
    }

    private ProfileManager(Context context) {
        this.context = context;
        databaseHelper = AppController.getDatabaseHelper();
        //dbHelper=AppController.getInstance().initializeSQLite();
    }




    public void createOrUpdateProfile(Profile profile, OnProfileCreatedListener onProfileCreatedListener) {
        createOrUpdateProfile(profile, null, onProfileCreatedListener);
    }


    public void createOrUpdateProfile(final Profile profile, Uri imageUri, final OnProfileCreatedListener onProfileCreatedListener) {
        if (imageUri == null) {
            //databaseHelper.createOrUpdateProfile(profile, onProfileCreatedListener);
            return;
        }

        String imageTitle = ImageUtil.generateImageTitle(UploadImagePrefix.PROFILE, profile.getPID());
        /*UploadTask uploadTask = databaseHelper.uploadImage(imageUri, imageTitle);

        if (uploadTask != null) {
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {



                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult().getUploadSessionUri();
                        Toast.makeText(context.getApplicationContext(), "successful upload image " , Toast.LENGTH_LONG).show();

                        profile.setProfilePicture(Uri.parse(downloadUrl.toString()));
                        databaseHelper.createOrUpdateProfile(profile, onProfileCreatedListener);

                    } else {
                        onProfileCreatedListener.onProfileCreated(false);
                        Toast.makeText(context.getApplicationContext(), "fail to upload image ", Toast.LENGTH_LONG).show();

                    }

                }
            });
        } else {
            onProfileCreatedListener.onProfileCreated(false);
            Toast.makeText(context.getApplicationContext(), "fail to upload image " , Toast.LENGTH_LONG).show();

        }*/
    }

    /*public void getProfileValue(Context activityContext, String id, final OnObjectChangedListenerSimple<Profile> listener) {
        //ValueEventListener valueEventListener = databaseHelper.getProfile(id, listener);
        //addListenerToMap(activityContext, valueEventListener);
    }*/


    public void getProfileSingleValue(String id, final OnObjectChangedListener<Profile> listener) {
        //databaseHelper.getProfileSingleValue(id, listener);
    }

    public ProfileStatus checkProfileFireBase() {
        Profile user = new Profile();

        if (user == null) {
            return ProfileStatus.NOT_AUTHORIZED;
        } else if (!PrefManager.isProfileCreated(context)){
            return ProfileStatus.NO_PROFILE;
        } else {
            return ProfileStatus.PROFILE_CREATED;
        }
    }
    public ProfileStatus checkProfilePref() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        user=sharedPreferences.getString("LastProfileUsed","");
        userProfile = gson.fromJson(user, Profile.class);

        if (userProfile == null) {
            return ProfileStatus.NOT_AUTHORIZED;
        } else if (!PrefManager.isProfileCreated(context)){
            return ProfileStatus.NO_PROFILE;
        } else {
            return ProfileStatus.PROFILE_CREATED;
        }
    }
    public ProfileStatus checkProfileSQLite(String phoneNumber) {
        profileDao= new ProfDAO(context.getApplicationContext());
        userProfile = profileDao.getUserDetailsFromPhoneNumber(phoneNumber);

        if (userProfile == null) {
            return ProfileStatus.NOT_AUTHORIZED;
        } else if (!PrefManager.isProfileCreated(context)){
            return ProfileStatus.NO_PROFILE;
        } else {
            return ProfileStatus.PROFILE_CREATED;
        }
    }
    public void logOut() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent i = new Intent(context.getApplicationContext(), SignTabMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }
}
