package com.skylightapp.MarketClasses;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

import static org.webrtc.ContextUtils.getApplicationContext;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

public class BackgroundListener implements LifecycleObserver {
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    Context context;


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onBackground() {
        QBSettings.getInstance().init(context, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        try {
            QBChatService.getInstance().logout(new QBEntityCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid, Bundle bundle) {
                    QBChatService.getInstance().destroy();
                }

                @Override
                public void onError(QBResponseException exception) {

                }
            });
            ChatHelper.getInstance().destroy();
            Log.d("BackgroundListener", "Background Successful");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}
