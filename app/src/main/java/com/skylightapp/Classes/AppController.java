package com.skylightapp.Classes;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;



@ReportsCrashes(mailTo = "lumgwun1@gmail.com", customReportContent = {
        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT}, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    //private FirebaseUser currentUser;
    private RequestQueue mRequestQueue;
    Profile userProfile;
    private DBHelper dbHelper;
    Context context;
    private static boolean isPersistenceEnabled = false;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static DatabaseHelper getDatabaseHelper() {
        return null;
    }

    /*public static DBHelper getSQLite() {
        return DBHelper.getHelper(mInstance);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initializeFirebase();
        //initializeSQLite();

        // The following line triggers the initialization of ACRA for crash Log Reposrting
        if (PreferenceHelper.getPrefernceHelperInstace().getBoolean(
                this, PreferenceHelper.SUBMIT_LOGS, true)) {
            ACRA.init(this);
        }
    }
    /*public DBHelper initializeSQLite() {
        try {
            dbHelper = DBHelper.getHelper(context);

            } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }*/

    public void initializeFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
       /* mFirebaseDatabase = FirebaseDatabase.getInstance();
        if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }
        mDatabase = mFirebaseDatabase.getReference();*/
    }

    /*public DatabaseReference getDatabaseReference() {
        if (mDatabase == null) {
            if (mFirebaseDatabase == null) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
            }
            mDatabase = mFirebaseDatabase.getReference();
        }
        return mDatabase;
    }*/

   // public void setUser1(FirebaseUser currentUser) {
      //  this.currentUser = currentUser;
    //}
    public void setUser(Profile currentUser) {
        this.userProfile = currentUser;
    }


    //public FirebaseUser getCurrentUser() {
      //  return currentUser;
    //}
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
