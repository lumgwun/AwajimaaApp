package com.skylightapp.VideoChat;

import androidx.multidex.MultiDexApplication;

import com.quickblox.auth.session.QBSettings;
import com.skylightapp.R;

public class AppV extends MultiDexApplication {
    //App credentials
    private static final String APPLICATION_ID = "";
    private static final String AUTH_KEY = "";
    private static final String AUTH_SECRET = "";
    private static final String ACCOUNT_KEY = "";

    public static final String USER_DEFAULT_PASSWORD = "quickblox";

    private static AppV instance;
    private QBResRequestExecutorV qbResRequestExecutorV;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        checkAppCredentials();
        initCredentials();
    }

    private void checkAppCredentials() {


        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
            throw new AssertionError(getString(R.string.error_credentials_empty));
        }
    }

    private void initCredentials() {
        QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        // Uncomment and put your Api and Chat servers endpoints if you want to point the sample
        // against your own server.
        //
        // QBSettings.getInstance().setEndpoints("https://your_api_endpoint.com", "your_chat_endpoint", ServiceZone.PRODUCTION);
        // QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
    }

    public synchronized QBResRequestExecutorV getQbResRequestExecutor() {
        return qbResRequestExecutorV == null
                ? qbResRequestExecutorV = new QBResRequestExecutorV()
                : qbResRequestExecutorV;
    }

    public static AppV getInstance() {
        return instance;
    }
}
