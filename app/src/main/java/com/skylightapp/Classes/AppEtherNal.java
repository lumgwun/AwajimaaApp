package com.skylightapp.Classes;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.ServiceZone;
import com.skylightapp.MarketClasses.ConfigUtils;
import com.skylightapp.MarketClasses.ImageLoader;
import com.skylightapp.MarketClasses.MyPreferences;
import com.skylightapp.MarketClasses.PreferencesManager;
import com.skylightapp.MarketClasses.QbConfigs;
import com.skylightapp.MarketClasses.SampleConfigs;
import com.skylightapp.MarketInterfaces.ConstsInterface;

import java.io.IOException;
import java.util.Calendar;

public class AppEtherNal extends Application {
    public static final String TAG = App.class.getSimpleName();

    private static AppEtherNal instance;
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
        ActivityLifecycle.init(this);

        initQbConfigs();

        AppEtherNal.context = getApplicationContext();
        preferencesManager = new PreferencesManager(AppEtherNal.context);
        preferences = preferencesManager.getMyPreferences();
        imageLoader = new ImageLoader();
        initSampleConfigs();
        initCredentials();
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

    public static synchronized AppEtherNal getInstance() {
        return instance;
    }

    public void initCredentials(){
        if (qbConfigs != null) {
            QBSettings.getInstance().init(getApplicationContext(), qbConfigs.getAppId(), qbConfigs.getAuthKey(), qbConfigs.getAuthSecret());
            QBSettings.getInstance().setAccountKey(qbConfigs.getAccountKey());

            if (!TextUtils.isEmpty(qbConfigs.getApiDomain()) && !TextUtils.isEmpty(qbConfigs.getChatDomain())) {
                QBSettings.getInstance().setEndpoints(qbConfigs.getApiDomain(), qbConfigs.getChatDomain(), ServiceZone.PRODUCTION);
                QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
            }
        }
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
        return AppEtherNal.context;
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
