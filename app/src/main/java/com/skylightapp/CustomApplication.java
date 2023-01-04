package com.skylightapp;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.BuildConfig.Teliver_API_Key;
import static com.skylightapp.Database.DBHelper.TAG;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.conference.ConferenceConfig;
import com.quickblox.core.ServiceZone;

import com.skylightapp.Interfaces.QBUsersHolder;
import com.skylightapp.MarketClasses.BackgroundListener;
import com.skylightapp.MarketClasses.ChatHelperCon;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.QBDialogsHolderConference;
import com.skylightapp.MarketClasses.QBDialogsHolderImplConf;
import com.skylightapp.MarketClasses.QBUsersHolderImpl;
import com.skylightapp.MarketClasses.SharedPrefsHelperCon;
import com.teliver.sdk.core.TLog;
import com.teliver.sdk.core.Teliver;



public class CustomApplication extends MultiDexApplication implements OnMapsSdkInitializedCallback {
    private static final String PREF_NAME = "awajima";
    private static CustomApplication mInstance;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;

    public static final String DEFAULT_USER_PASSWORD = "quickblox";
    private static final String SERVER_URL = "";

    //Chat settings range

    private static boolean isPersistenceEnabled = false;

    private static final String QB_USER_ID = "qb_user_id";
    private static final String QB_USER_LOGIN = "qb_user_login";
    private static final String QB_USER_PASSWORD = "qb_user_password";
    private static final String QB_USER_FULL_NAME = "qb_user_full_name";
    private static final String QB_USER_TAGS = "qb_user_tags";

    private SharedPreferences sharedPreferences;
    public static final int CHAT_PORT = 5223;
    public static final int SOCKET_TIMEOUT = 300;
    public static final boolean KEEP_ALIVE = true;
    public static final boolean USE_TLS = true;
    public static final boolean AUTO_JOIN = false;
    public static final boolean AUTO_MARK_DELIVERED = true;
    public static final boolean RECONNECTION_ALLOWED = true;
    public static final boolean ALLOW_LISTEN_NETWORK = true;

    //Chat settings range
    private static final int MAX_PORT_VALUE = 65535;
    private static final int MIN_PORT_VALUE = 1000;
    private static final int MIN_SOCKET_TIMEOUT = 300;
    private static final int MAX_SOCKET_TIMEOUT = 60000;
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    private SharedPrefsHelperCon sharedPrefsHelper;
    private QBUsersHolder qbUsersHolder;
    private QBDialogsHolderConference qbDialogsHolder;
    private ChatHelperCon chatHelper;
    private DialogsManager dialogsManager;
    @Override
    public void onCreate() {
        super.onCreate();
        //Teliver.init(this,Teliver_API_Key);
        //TLog.setVisible(true);
        mInstance = this;
        //MixpanelAPI.getInstance(this, BuildConfig.MIXPANEL_TOKEN);
        initializeFirebase();
        //MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LATEST, this);
        //MapsInitializer.initialize(getApplicationContext(), Renderer.LEGACY, this);
       // ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());
        //checkAppCredentials();
        //checkChatSettings();
        //initCredentials();
        //initConferenceConfig();
        //initUsersHolder();
        //initDialogsHolder();
        //initChatHelper();
        //initDialogsManager();

    }
    public void initializeFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }

    }

    public static synchronized CustomApplication getInstance() {
        if (mInstance == null) {
            mInstance = new CustomApplication();
        }

        return mInstance;
    }
    private void initUsersHolder() {
        qbUsersHolder = new QBUsersHolderImpl();
    }

    public QBUsersHolder getQBUsersHolder() {
        return qbUsersHolder;
    }

    private void initDialogsHolder() {
        qbDialogsHolder = new QBDialogsHolderImplConf();
    }

    public QBDialogsHolderConference getQBDialogsHolder() {
        return qbDialogsHolder;
    }

    private void initChatHelper() {
        chatHelper = new ChatHelperCon(getApplicationContext());
    }

    public ChatHelperCon getChatHelper() {
        return chatHelper;
    }

    private void initDialogsManager() {
        dialogsManager = new DialogsManager(getApplicationContext());
    }

    public DialogsManager getDialogsManager() {
        return dialogsManager;
    }

    private void checkAppCredentials() {
        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
            throw new AssertionError(getString(R.string.error_qb_credentials_empty));
        }
    }

    private void checkChatSettings() {
        if (USER_DEFAULT_PASSWORD.isEmpty() || (CHAT_PORT < MIN_PORT_VALUE || CHAT_PORT > MAX_PORT_VALUE)
                || (SOCKET_TIMEOUT < MIN_SOCKET_TIMEOUT || SOCKET_TIMEOUT > MAX_SOCKET_TIMEOUT)) {
            throw new AssertionError(getString(R.string.error_chat_credentails_empty));
        }
    }

    private void initCredentials() {
        QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        // Uncomment and put your Api and Chat servers endpoints if you want to point the sample
        // against your own server.
        //
        // QBSettings.getInstance().setEndpoints("https://your.api.endpoint.com", "your.chat.endpoint.com", ServiceZone.PRODUCTION);
        QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
    }

    private void initConferenceConfig() {
        if (!TextUtils.isEmpty(SERVER_URL)) {
            ConferenceConfig.setUrl(SERVER_URL);
        } else {
            throw new AssertionError(getString(R.string.error_server_url_null));
        }
    }

    @Override
    public void onMapsSdkInitialized(@NonNull MapsInitializer.Renderer renderer) {

    }
}
