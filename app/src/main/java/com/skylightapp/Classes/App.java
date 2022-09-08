package com.skylightapp.Classes;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.ServiceZone;
import com.quickblox.messages.services.QBPushManager;
import com.skylightapp.MarketClasses.BackgroundListener;
import com.skylightapp.MarketClasses.ConfigUtils;
import com.skylightapp.MarketClasses.CoreConfigUtils;
import com.skylightapp.MarketClasses.ImageLoader;
import com.skylightapp.MarketClasses.MyPreferences;
import com.skylightapp.MarketClasses.PreferencesManager;
import com.skylightapp.MarketClasses.QbConfigs;
import com.skylightapp.MarketClasses.SampleConfigs;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.R;

import java.io.IOException;
import java.util.Calendar;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private static final String APPLICATION_ID = "98089";
    private static final String AUTH_KEY = "DBsWv9QDreRB3eV";
    private static final String AUTH_SECRET = "MCPCHcbddH4BuKV";
    private static final String ACCOUNT_KEY = "8_x7t1uYpTckdzzrYbEa";

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

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //ActivityLifecycle.init();
        checkConfig();
        initCredentials();
        initQBSessionManager();
        initPushManager();
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
}
