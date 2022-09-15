package com.skylightapp.MarketClasses;

import android.util.Log;

import com.quickblox.conference.ConferenceSession;

public class WebRtcSessionManagerCon {
    private static final String TAG = WebRtcSessionManager.class.getSimpleName();

    private static WebRtcSessionManagerCon instance;
    private static ConferenceSession currentSession;

    private WebRtcSessionManagerCon() {

    }

    public static WebRtcSessionManagerCon getInstance() {
        if (instance == null) {
            Log.d(TAG, "New Instance of WebRtcSessionManager");
            instance = new WebRtcSessionManagerCon();
        }

        return instance;
    }

    public ConferenceSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(ConferenceSession qbCurrentSession) {
        currentSession = qbCurrentSession;
    }
}
