package com.skylightapp.MarketClasses;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;



import com.quickblox.auth.session.QBSettings;
import com.quickblox.conference.ConferenceConfig;
import com.skylightapp.Interfaces.QBUsersHolder;
import com.skylightapp.R;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class AppConference extends MultiDexApplication {
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

    //App credentials
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";

    public static final String USER_DEFAULT_PASSWORD = "quickblox";

    private static SharedPrefsHelperCon sharedPrefsHelperCon;
    private QBUsersHolder qbUsersHolder;
    private QBDialogsHolderConference qbDialogsHolder;
    private ChatHelperCon chatHelper;
    private DialogsManager dialogsManager;
    private static AppConference instance;
    private static PreferencesManager preferencesManager;
    private static MyPreferences preferences;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ActivityLifecycle.init(this);
        checkAppCredentials();
        checkChatSettings();
        initCredentials();
        initConferenceConfig();
        initSharedPreferences();
        initUsersHolder();
        initDialogsHolder();
        initChatHelper();
        initDialogsManager();
        preferencesManager = new PreferencesManager(AppConference.context);
        preferences = preferencesManager.getMyPreferences();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());
    }
    public void initApplication() {
        instance = this;
    }
    public static synchronized AppConference getInstance() {
        return instance;
    }

    public void initSharedPreferences() {
        sharedPrefsHelperCon = new SharedPrefsHelperCon(getApplicationContext());
    }
    public static MyPreferences getPreferences() {
        return preferences;
    }

    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public static SharedPrefsHelperCon getSharedPrefsHelper() {
        return sharedPrefsHelperCon;
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
        // QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
    }

    private void initConferenceConfig() {
        if (!TextUtils.isEmpty(SERVER_URL)) {
            ConferenceConfig.setUrl(SERVER_URL);
        } else {
            throw new AssertionError(getString(R.string.error_server_url_null));
        }
    }
}
