package com.skylightapp.Classes;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Message;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import com.google.gson.Gson;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.model.QBUser;
import com.skylightapp.CustomApplication;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.Fence;
import com.skylightapp.MapAndLoc.FenceEvent;
import com.skylightapp.MapAndLoc.GeofenceBroadcastReceiver;
import com.skylightapp.MapAndLoc.ReportSceneMAct;
import com.skylightapp.MarketClasses.BackgroundListener;
import com.skylightapp.MarketClasses.MyPreferences;
import com.skylightapp.MarketClasses.SampleConfigs;
import com.skylightapp.MarketClasses.MarketStock;
import com.skylightapp.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_DWELL;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_ENTER;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_EXIT;
import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.BuildConfig.Teliver_API_Key;


@ReportsCrashes(mailTo = "lumgwun1@gmail.com", customReportContent = {
        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT}, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)

public class AppController extends CustomApplication {

    public static final String TAG = AppController.class.getSimpleName();
    private static final String CLASSTAG =
            " " + AppController.class.getSimpleName() + " ";

    private static AppController mInstance;
    //private FirebaseUser currentUser;
    private RequestQueue mRequestQueue;
    Profile userProfile;
    private DBHelper dbHelper;
    Context context;
    private SharedPreferences userPreferences;

    private static final String QB_CONFIG_DEFAULT_FILE_NAME = "qb_config.json";
    private static SampleConfigs sampleConfigs;

    public static int year = Calendar.getInstance().get(Calendar.YEAR);

    private static MyPreferences preferences;
    private static Gson gson = new Gson();

    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    public static final int CHAT_PORT = 5223;
    public static final int SOCKET_TIMEOUT = 300;
    public static final boolean KEEP_ALIVE = true;
    public static final boolean USE_TLS = true;
    public static final boolean AUTO_JOIN = false;
    public static final boolean AUTO_MARK_DELIVERED = true;
    public static final boolean RECONNECTION_ALLOWED = true;
    public static final boolean ALLOW_LISTEN_NETWORK = true;

    //App credentials
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;

    public static final String DEFAULT_USER_PASSWORD = "quickblox";
    private static final String SERVER_URL = "";

    //Chat settings range
    private static final int MAX_PORT_VALUE = 65535;
    private static final int MIN_PORT_VALUE = 1000;
    private static final int MIN_SOCKET_TIMEOUT = 300;
    private static final int MAX_SOCKET_TIMEOUT = 60000;

    private static boolean isPersistenceEnabled = false;

    private static final String QB_USER_ID = "qb_user_id";
    private static final String QB_USER_LOGIN = "qb_user_login";
    private static final String QB_USER_PASSWORD = "qb_user_password";
    private static final String QB_USER_FULL_NAME = "qb_user_full_name";
    private static final String QB_USER_TAGS = "qb_user_tags";
    private PrefManager sharedPrefsHelper;
    private static final String PREF_NAME = "awajima";


    private SharedPreferences sharedPreferences;
    public static String DAY_NIGHT_MODE = "dayNightMode";

    // chrome://inspect


    public static DatabaseHelper getDatabaseHelper() {
        return null;
    }

    private MarketStock currentProd = null;

    public MarketStock getCurrentProd() {
        return currentProd;
    }

    public void setCurrentProd(MarketStock currentProd) {
        this.currentProd = currentProd;
    }

    public boolean isHasBackgroundLocationPermission() {
        return hasBackgroundLocationPermission;
    }

    public void setHasBackgroundLocationPermission(boolean hasBackgroundLocationPermission) {
        this.hasBackgroundLocationPermission = hasBackgroundLocationPermission;
    }

    public List<Fence> getFences() {
        return fences;
    }

    public void setFences(List<Fence> fences) {
        this.fences = (ArrayList<Fence>) fences;
    }

    public CopyOnWriteArrayList<FenceEvent> getEvents() {
        return events;
    }

    public void setEvents(CopyOnWriteArrayList<FenceEvent> events) {
        this.events = events;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isHasCoarseLocationPermission() {
        return hasCoarseLocationPermission;
    }

    public void setHasCoarseLocationPermission(boolean hasCoarseLocationPermission) {
        this.hasCoarseLocationPermission = hasCoarseLocationPermission;
    }

    public boolean isHasFineLocationPermission() {
        return hasFineLocationPermission;
    }

    public void setHasFineLocationPermission(boolean hasFineLocationPermission) {
        this.hasFineLocationPermission = hasFineLocationPermission;
    }



    public enum Status {DEFAULT, FENCES_ADDED, FENCES_FAILED, FENCES_REMOVED}

    private FusedLocationProviderClient fusedLocationClient;
    private GeofencingClient geofencingClient;


    ArrayList<Fence> fences;


    CopyOnWriteArrayList<FenceEvent> events;


    private boolean hasBackgroundLocationPermission;


    private boolean hasCoarseLocationPermission;

    private boolean hasFineLocationPermission;

    public boolean hasForegroundLocationPermission() {
        return hasCoarseLocationPermission || hasFineLocationPermission;
    }

    private Status status;

    private AlertDialog addingFenceFailedDialog;

    private Activity dialogActivity;

    private PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        //Teliver.init(this,Teliver_API_Key);
        mInstance = this;
        initDayNight();
        initializeFirebase();
        Stetho.initializeWithDefaults(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());
        //initializeSQLite();

        // The following line triggers the initialization of ACRA for crash Log Reposrting
        if (PreferenceHelper.getPrefernceHelperInstace().getBoolean(
                this, PreferenceHelper.SUBMIT_LOGS, true)) {
            ACRA.init(this);
        }
        //super.onCreate ();
        //Log.v (Constants.LOGTAG, CLASSTAG + "Application onCreate called");

        status = Status.DEFAULT;
        events = new CopyOnWriteArrayList<>();
        fences = new ArrayList<>();

        // 50 metre geofence centred on PH. Tech Creek
        Fence fence = Fence.buildCircularFence("PH. Tech Creek",
                new LatLng(4.8156d, 7.0498d), 100f);
        fences.add(fence);
    }

    public Task<Location> getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

        }
        return fusedLocationClient.getLastLocation();
    }

    public Task<LocationAvailability> getLocationAvailability() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

        }
        return fusedLocationClient.getLocationAvailability();
    }

    public @NotNull
    String getStatusText() {
        //Log.v (Constants.LOGTAG, CLASSTAG + "Requesting status text for status: " + status);
        int resId;
        switch (status) {
            case DEFAULT:
                resId = R.string.status_default;
                break;
            case FENCES_ADDED:
                resId = R.string.status_fences_added;
                break;
            case FENCES_FAILED:
                resId = R.string.status_fences_failed;
                break;
            case FENCES_REMOVED:
                resId = R.string.status_fences_removed;
                break;
            default:
                resId = R.string.status_unknown;
        }
        String result = getResources().getString(resId);
        //Log.v (Constants.LOGTAG, CLASSTAG + "Returning status text: " + result);
        return result;
    }

    public String getTransitionString(Resources resources, int transition) {
        String result;
        switch (transition) {
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                result = resources.getString(R.string.geofence_event_dwell);
                break;
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                result = resources.getString(R.string.geofence_event_enter);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                result = resources.getString(R.string.geofence_event_exit);
                break;
            default:
                result = resources.getString(R.string.geofence_event_unknown);

        }
        return result;
    }

    public boolean isGeofencingInitialised() {
        return geofencingClient != null;
    }


    public void initGeofencing(Activity activity) {
        // Initialise the fused location client if needed.
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }

        if (isHasBackgroundLocationPermission()) {
            if (!isGeofencingInitialised()) {
                // Initialise the geofencing
                geofencingClient = LocationServices.getGeofencingClient(this);
                Intent intent = new Intent(activity, GeofenceBroadcastReceiver.class);
                pendingIntent =
                        PendingIntent.getBroadcast(this, 0, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);
            }
        }
    }

    public void addFences(Activity activity) {
        /*Log.v ( Constants.LOGTAG, CLASSTAG +
                "Adding fences via GeofencingRequest.Builder" );*/

        // Create the request builder
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder()
                .setInitialTrigger(INITIAL_TRIGGER_DWELL | INITIAL_TRIGGER_ENTER |
                        INITIAL_TRIGGER_EXIT);

        // Add the fences
        for (Fence fence : fences) {
            builder.addGeofence(fence.createGeofence());
        }

        // Add the geofences
        dialogActivity = activity;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return;
        }
        geofencingClient.addGeofences(builder.build(), pendingIntent)
                .addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        handleAddingFenceSucceeded();
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        handleAddingFenceFailed(e);
                    }
                });
    }

    public Task<Void> requestSingleLocation(LocationCallback callback) {
        LocationRequest request = LocationRequest.create()
                .setNumUpdates(1)
                .setExpirationDuration(5000)   // Give up if no location in 5 seconds
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // return fusedLocationClient.requestLocationUpdates (request, pendingIntent);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

        }
        return fusedLocationClient.requestLocationUpdates(request, callback, getMainLooper());
    }

    private void createAddingFenceFailedDialog (String msg)
    {
       // Log.v (Constants.LOGTAG, CLASSTAG + ">createAddingFenceFailedDialog");
        addingFenceFailedDialog = new AlertDialog.Builder (dialogActivity)
                .setTitle (R.string.adding_fence_failed_title)
                .setMessage (msg)
                .setPositiveButton (R.string.ok, new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        handleCloseAddingFenceFailedDialog ();
                    }
                })
                .create ();
    }

    private void handleAddingFenceFailed (Exception e)
    {
        String logMsg = CLASSTAG + "Adding fence failed: " + e.getLocalizedMessage ();
        //Log.w (Constants.LOGTAG, logMsg, e);
        String msg;
        Resources res = getResources ();
        if (e instanceof ApiException)
        {
            int code = ((ApiException) e).getStatusCode ();
            switch (code)
            {
                case 1004:  // GeofenceStatusCodes.GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_insufficient_location_permission) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_not_available) );
                    break;
                case 1005: //GeofenceStatusCodes.GEOFENCE_REQUEST_TOO_FREQUENT
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_request_too_frequent) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_geofences) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_pending_intents) );
                    break;
                default:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            e.getLocalizedMessage () );
            }
        } else
        {
            msg = res.getString ( R.string.failed_with_unexpected_error,
                    e.getLocalizedMessage () );
        }
        createAddingFenceFailedDialog (msg);
        addingFenceFailedDialog.show ();
    }

    private void handleAddingFenceSucceeded ()
    {
        //Log.i (Constants.LOGTAG, CLASSTAG + "Fence added.");
        status = Status.FENCES_ADDED;
        // Force a screen refresh on DisplayLocationActivity
        ReportSceneMAct.getHandler ().handleMessage (new Message());
    }

    private void handleCloseAddingFenceFailedDialog ()
    {
        //Log.v (Constants.LOGTAG, CLASSTAG + "handleCloseAddingFenceFailedDialog called");
        addingFenceFailedDialog = null;
        Activity activity = dialogActivity;
        dialogActivity = null;
        status = Status.FENCES_FAILED;
        // Force a screen refresh on DisplayLocationActivity
        ReportSceneMAct.getHandler ().handleMessage (new Message());
    }
    public void initDayNight(){
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(DAY_NIGHT_MODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
        }

        return mInstance;
    }

    public AppController() {
        mInstance = this;
        try {

            sharedPreferences = AppController.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //sharedPreferences = mInstance.getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            getEditor().remove(key).commit();
        }
    }

    public void save(String key, Object value) {
        SharedPreferences.Editor editor = getEditor();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-supported preference");
        }

        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }
    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }


    public void saveQbUser(QBUser qbUser) {
        save(QB_USER_ID, qbUser.getId());
        save(QB_USER_LOGIN, qbUser.getLogin());
        save(QB_USER_PASSWORD, qbUser.getPassword());
        save(QB_USER_FULL_NAME, qbUser.getFullName());
        save(QB_USER_TAGS, qbUser.getTags().getItemsAsString());
    }

    public void removeQbUser() {
        delete(QB_USER_ID);
        delete(QB_USER_LOGIN);
        delete(QB_USER_PASSWORD);
        delete(QB_USER_FULL_NAME);
        delete(QB_USER_TAGS);
    }
    public void clearAllData() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear().commit();
    }

    public QBUser getQbUser() {
        if (hasQbUser()) {
            Integer id = get(QB_USER_ID);
            String login = get(QB_USER_LOGIN);
            String password = get(QB_USER_PASSWORD);
            String fullName = get(QB_USER_FULL_NAME);
            String tagsInString = get(QB_USER_TAGS);

            StringifyArrayList<String> tags = null;

            if (tagsInString != null) {
                tags = new StringifyArrayList<>();
                tags.add(tagsInString.split(","));
            }

            QBUser user = new QBUser(login, password);
            user.setId(id);
            user.setFullName(fullName);
            user.setTags(tags);
            return user;
        } else {
            return null;
        }
    }

    public boolean hasQbUser() {
        return has(QB_USER_LOGIN) && has(QB_USER_PASSWORD);
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
        //FirebaseApp.initializeApp(getApplicationContext());
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
