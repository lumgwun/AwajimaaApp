package com.skylightapp.Classes;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

@SuppressWarnings("deprecation")
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedToken = FirebaseInstanceId.getInstance().toString();
        LogUtil.logDebug(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //ProfileInteractor.getInstance(getApplicationContext()).updateRegistrationToken(token);
    }
}
