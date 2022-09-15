package com.skylightapp.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.conference.ConferenceConfig;
import com.quickblox.core.ServiceZone;
import com.quickblox.messages.services.QBPushManager;
import com.skylightapp.Interfaces.QBDialogsHolder;
import com.skylightapp.Interfaces.QBUsersHolder;
import com.skylightapp.MarketClasses.QBDialogsHolderImpl;
import com.skylightapp.MarketClasses.QBUsersHolderImpl;
import com.skylightapp.MarketClasses.BackgroundListener;
import com.skylightapp.MarketClasses.ChatHelper;
import com.skylightapp.MarketClasses.ConfigUtils;
import com.skylightapp.MarketClasses.CoreConfigUtils;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.ImageLoader;
import com.skylightapp.MarketClasses.MyPreferences;
import com.skylightapp.MarketClasses.PreferencesManager;
import com.skylightapp.MarketClasses.QbConfigs;
import com.skylightapp.MarketClasses.SampleConfigs;
import com.skylightapp.MarketClasses.SharedPrefsHelperCon;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.R;

import java.io.IOException;
import java.util.Calendar;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class App extends MultiDexApplication {
    private static final String TAG = App.class.getSimpleName();
    private static final String PREF_NAME = "skylight";

    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;

    public static final String DEFAULT_USER_PASSWORD = "quickblox";

    private static App instance;

    private static final String QB_CONFIG_DEFAULT_FILE_NAME = "qb_config.json";
    private static QbConfigs qbConfigs;
    private static SampleConfigs sampleConfigs;
    private static ImageLoader imageLoader;

    private static Context context;
    public static int year = Calendar.getInstance().get(Calendar.YEAR);

    private static PreferencesManager preferencesManager;
    private static MyPreferences preferences;
    private static Gson gson = new Gson();


    private QBUsersHolder qbUsersHolder;
    private QBDialogsHolder qbDialogsHolder;
    private ChatHelper chatHelper;
    private DialogsManager dialogsManager;
    private SharedPreferences userPreferences;


    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    public static final int CHAT_PORT = 5223;
    public static final int SOCKET_TIMEOUT = 300;
    public static final boolean KEEP_ALIVE = true;
    public static final boolean USE_TLS = true;
    public static final boolean AUTO_JOIN = false;
    public static final boolean AUTO_MARK_DELIVERED = true;
    public static final boolean RECONNECTION_ALLOWED = true;
    public static final boolean ALLOW_LISTEN_NETWORK = true;

    private static final String SERVER_URL = "";

    //Chat settings range
    private static final int MAX_PORT_VALUE = 65535;
    private static final int MIN_PORT_VALUE = 1000;
    private static final int MIN_SOCKET_TIMEOUT = 300;
    private static final int MAX_SOCKET_TIMEOUT = 60000;
    private QBResRequestExecutor qbResRequestExecutor;

    private SharedPrefsHelperCon sharedPrefsHelperCon;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //ActivityLifecycle.init();
        checkConfig();
        initCredentials();
        initQBSessionManager();
        initPushManager();

        App.context = getApplicationContext();
        preferencesManager = new PreferencesManager(App.context);
        preferences = preferencesManager.getMyPreferences();
        imageLoader = new ImageLoader();
        initConferenceConfig();
        initCredentials();
        checkConfig();

        initApplication();
        checkAppCredentials();
        checkChatSettings();
        initSharedPreferences();
        initUsersHolder();
        initDialogsHolder();
        initChatHelper();
        initDialogsManager();


        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());

        initQbConfigs();

        App.context = getApplicationContext();
        preferencesManager = new PreferencesManager(App.context);
        preferences = preferencesManager.getMyPreferences();
        imageLoader = new ImageLoader();
        initSampleConfigs();

    }

    private void checkConfig() {
        if (TextUtils.isEmpty(APPLICATION_ID) || TextUtils.isEmpty(AUTH_KEY) || TextUtils.isEmpty(AUTH_SECRET) || TextUtils.isEmpty(ACCOUNT_KEY)
                || TextUtils.isEmpty(DEFAULT_USER_PASSWORD)) {
            throw new AssertionError(getString(R.string.error_qb_credentials_empty));
        }
    }
    public void initCredentials(){
        if (qbConfigs != null) {
            //QBSettings.getInstance().init(getApplicationContext(), qbConfigs.getAppId(), qbConfigs.getAuthKey(), qbConfigs.getAuthSecret());
            //QBSettings.getInstance().setAccountKey(qbConfigs.getAccountKey());

            QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
            QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

            if (!TextUtils.isEmpty(qbConfigs.getApiDomain()) && !TextUtils.isEmpty(qbConfigs.getChatDomain())) {
                QBSettings.getInstance().setEndpoints(qbConfigs.getApiDomain(), qbConfigs.getChatDomain(), ServiceZone.PRODUCTION);
                QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
            }


        }
    }


    private void initQBSessionManager() {
        QBSessionManager.getInstance().addListener(new QBSessionManager.QBSessionListener() {
            @Override
            public void onSessionCreated(QBSession qbSession) {
                Log.d(TAG, "Session Created");
            }

            @Override
            public void onSessionUpdated(QBSessionParameters qbSessionParameters) {
                Log.d(TAG, "Session Updated");
            }

            @Override
            public void onSessionDeleted() {
                Log.d(TAG, "Session Deleted");
            }

            @Override
            public void onSessionRestored(QBSession qbSession) {
                Log.d(TAG, "Session Restored");
            }

            @Override
            public void onSessionExpired() {
                Log.d(TAG, "Session Expired");
            }

            @Override
            public void onProviderSessionExpired(String provider) {
                Log.d(TAG, "Session Expired for provider:" + provider);
            }
        });
    }

    private void initPushManager() {
        QBPushManager.getInstance().addListener(new QBPushManager.QBSubscribeListener() {
            @Override
            public void onSubscriptionCreated() {
                ToastUtils.shortToast("Subscription Created");
                Log.d(TAG, "Subscription Created");
            }

            @Override
            public void onSubscriptionError(Exception e, int resultCode) {
                Log.d(TAG, "SubscriptionError" + e.getLocalizedMessage());
                if (resultCode >= 0) {
                    String error = GoogleApiAvailability.getInstance().getErrorString(resultCode);
                    Log.d(TAG, "SubscriptionError playServicesAbility: " + error);
                }
                ToastUtils.shortToast(e.getLocalizedMessage());
            }

            @Override
            public void onSubscriptionDeleted(boolean success) {
                Log.d(TAG, "Subscription Deleted");
            }
        });
    }


    private void initSampleConfigs() {
        try {
            sampleConfigs = ConfigUtils.getSampleConfigs(ConstsInterface.SAMPLE_CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SampleConfigs getSampleConfigs() {
        return sampleConfigs;
    }

    private void initQbConfigs() {
        Log.e(TAG, "QB CONFIG FILE NAME: " + getQbConfigFileName());
        qbConfigs = CoreConfigUtils.getCoreConfigsOrNull(getQbConfigFileName());
    }

    public static synchronized App getInstance() {
        return instance;
    }



    public static Gson getGson() {
        return gson;
    }

    public static QbConfigs getQbConfigs(){
        return qbConfigs;
    }

    public static String getQbConfigFileName(){
        return QB_CONFIG_DEFAULT_FILE_NAME;
    }

    public static Context getAppContext() {
        return App.context;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static MyPreferences getPreferences() {
        return preferences;
    }

    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }
    private void initApplication() {
        instance = this;
    }
    private void initUsersHolder() {
        qbUsersHolder = new QBUsersHolderImpl();
    }
    private void initSharedPreferences() {
        sharedPrefsHelperCon = new SharedPrefsHelperCon(getApplicationContext());
    }




    public QBUsersHolder getQBUsersHolder() {
        return qbUsersHolder;
    }

    private void initDialogsHolder() {
        qbDialogsHolder = new QBDialogsHolderImpl();
    }

    public QBDialogsHolder getQBDialogsHolder() {
        return qbDialogsHolder;
    }

    private void initChatHelper() {
        chatHelper = new ChatHelper(getApplicationContext());
    }

    public ChatHelper getChatHelper() {
        return chatHelper;
    }

    private void initDialogsManager() {
        dialogsManager = new DialogsManager(getApplicationContext());
    }

    public DialogsManager getDialogsManager() {
        return dialogsManager;
    }

    public void clearAllData() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear().commit();
    }


    public SharedPrefsHelperCon getSharedPrefsHelper() {
        return sharedPrefsHelperCon;
    }

    private SharedPreferences.Editor getEditor() {
        if (userPreferences == null)
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return userPreferences.edit();
    }



    private void initConferenceConfig() {
        if (!TextUtils.isEmpty(SERVER_URL)) {
            ConferenceConfig.setUrl(SERVER_URL);
        } else {
            throw new AssertionError(getString(R.string.error_server_url_null));
        }
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





    /*private void initSampleConfigs() {
        try {
            sampleConfigs = ConfigUtils.getSampleConfigs(Consts.SAMPLE_CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
